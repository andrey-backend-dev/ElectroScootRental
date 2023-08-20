package com.example.electroscoot.services;

import com.example.electroscoot.dao.RoleRepository;
import com.example.electroscoot.dao.UserRepository;
import com.example.electroscoot.dto.*;
import com.example.electroscoot.entities.Role;
import com.example.electroscoot.entities.User;
import com.example.electroscoot.exceptions.CustomConflictException;
import com.example.electroscoot.services.interfaces.IUserService;
import com.example.electroscoot.utils.enums.UserStatus;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Validated
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Clock clock;
    @Value("${business.defaultRole}")
    private String defaultRole;
    @Value("${business.subscriptionCost}")
    private float subscriptionCost;

    @Override
    @Transactional
    public AuthenticationDTO register(@Valid RegistrationDTO registrationData) {

        Role role = roleRepository.findByName(defaultRole).orElseThrow(() -> {
            return new IllegalArgumentException("The role with name " + defaultRole + " does not exist.");
        });

        User user = User.builder()
                .password(passwordEncoder.encode(registrationData.getPassword()))
                .phone(formatPhone(registrationData.getPhone()))
                .username(registrationData.getUsername())
                .status(UserStatus.OK)
                .roles(new HashSet<>(Set.of(role)))
                .registeredSince(LocalDateTime.now(clock))
                .build();

        String jwt = jwtService.generateJWT(user);

        return new AuthenticationDTO(new UserDTO(userRepository.save(user)), jwt);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthenticationDTO login(@Valid LoginDTO loginData) {
        String username = loginData.getUsername();
        String password = loginData.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            return new IllegalArgumentException("The user with username " + username + " does not exist.");
        });

        authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        String jwt = jwtService.generateJWT(user);

        return new AuthenticationDTO(new UserDTO(user), jwt);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO findById(@Positive(message = "Id must be more than zero.") int id) {
        return new UserDTO(userRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("The user with id " + id + " does not exist.");
        }));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO findByUsername(@NotBlank(message = "Username is mandatory.") String username) {
        return new UserDTO(userRepository.findByUsername(username).orElseThrow(() -> {
            return new IllegalArgumentException("The user with username " + username + " does not exist.");
        }));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getList() {
        return ((List<User>) userRepository.findAll()).stream().map(UserDTO::new).toList();
    }

    @Override
    @Transactional
    public boolean deleteByUsername(@NotBlank(message = "Username is mandatory.") String username) {
        userRepository.findByUsername(username).orElseThrow(() -> {
            return new IllegalArgumentException("The user with username " + username + " does not exist.");
        });

        userRepository.deleteByUsername(username);

        return userRepository.findByUsername(username).orElse(null) == null;
    }

    @Override
    @Transactional
    public UserDTO updateByUsername(@NotBlank(message = "Username is mandatory.") String username,
                                    UpdateUserDTO updateData) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            return new IllegalArgumentException("The user with username " + username + " does not exist.");
        });
        String firstname = updateData.getFirstname();
        if (firstname != null) {
            user.setFirstname(firstname);
        }
        String secondname = updateData.getSecondname();
        if (secondname != null) {
            user.setSecondname(secondname);
        }
        String email = updateData.getEmail();
        if (email != null) {
            user.setEmail(getEmailIfValid(email));
        }
        String newUsername = updateData.getUsername();
        if (newUsername != null) {
            user.setUsername(getUsernameIfValid(newUsername));
        }
        String phone = updateData.getPhone();
        if (phone != null) {
            user.setPhone(getPhoneIfValid(phone));
        }
        return new UserDTO(user);
    }


    @Override
    @Transactional(readOnly = true)
    public List<RoleDTO> getRolesByUsername(@NotBlank(message = "Username is mandatory.") String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            return new IllegalArgumentException("The user with username " + username + " does not exist.");
        });

        return user.getRoles().stream().map(RoleDTO::new).toList();
    }

    @Override
    @Transactional
    public List<RoleDTO> addRoleByUsername(@NotBlank(message = "Username is mandatory.") String username,
                                           @Valid RoleNameDTO roleNameDTO) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            return new IllegalArgumentException("The user with username " + username + " does not exist.");
        });
        Role role = roleRepository.findByName(roleNameDTO.getName()).orElseThrow(() -> {
            return new IllegalArgumentException("The role with name " + roleNameDTO.getName() + " does not exist.");
        });

        Set<Role> roles = user.getRoles();
        if (roles.contains(role)) {
            throw new IllegalArgumentException("The user with username " + username + " already has the role with name " + roleNameDTO.getName());
        }

        roles.add(role);

        return user.getRoles().stream().map(RoleDTO::new).toList();
    }

    @Override
    @Transactional
    public List<RoleDTO> removeRoleByUsername(@NotBlank(message = "Username is mandatory.") String username,
                                              @Valid RoleNameDTO roleNameDTO) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            return new IllegalArgumentException("The user with username " + username + " does not exist.");
        });
        Role role = roleRepository.findByName(roleNameDTO.getName()).orElseThrow(() -> {
            return new IllegalArgumentException("The role with name " + roleNameDTO.getName() + " does not exist.");
        });

        Set<Role> roles = user.getRoles();
        if (!roles.contains(role)) {
            throw new IllegalArgumentException("The user with username " + username + " does not have the role with name " + roleNameDTO.getName());
        }

        roles.remove(role);

        return user.getRoles().stream().map(RoleDTO::new).toList();
    }

    @Override
    @Transactional
    public UserDTO addMoneyByUsername(@NotBlank(message = "Username is mandatory.") String username,
                                      @Valid MoneyDTO moneyDTO) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            return new IllegalArgumentException("The user with username " + username + " does not exist.");
        });
        user.setMoney(user.getMoney() + moneyDTO.getMoney());
        return new UserDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScooterRentalDTO> getRentHistoryByUsername(@NotBlank(message = "Username is mandatory.") String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            return new IllegalArgumentException("The user with username " + username + " does not exist.");
        });
        return user.getScooterRentals().stream().map(ScooterRentalDTO::new).toList();
    }

    @Override
    @Transactional
    public UserDTO buySubscriptionByUsername(@NotBlank(message = "Username is mandatory.") String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            return new IllegalArgumentException("The user with username " + username + " does not exist.");
        });

        if (user.getSubscriptionTill() != null && user.getSubscriptionTill().isAfter(LocalDateTime.now(clock))) {
            throw new IllegalArgumentException("The user with username " + username + " has already bought a subscription." +
                    " Subscription is active till: " + user.getSubscriptionTill());
        }

        float money = user.getMoney();
        if (money >= subscriptionCost) {
            user.setMoney(money - subscriptionCost);
            user.setSubscriptionTill(LocalDateTime.now(clock).plusMonths(1));
        } else {
            throw new IllegalArgumentException(user.getUsername() + " does not have enough money to buy subscription.");
        }

        return new UserDTO(user);
    }

    @Override
    @Transactional
    public UserDTO changeUserStatusByUsername(@NotBlank(message = "Username is mandatory.") String username, UserStatus status) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            return new IllegalArgumentException("The user with username " + username + " does not exist.");
        });

        if (status == null) {
            throw new ConstraintViolationException("User status is not valid.", null);
        }

        if (user.getStatus() == status) {
            throw new CustomConflictException(String.format("The user with username %s already has %s status.", username, status.getName()));
        }

        user.setStatus(status);

        return new UserDTO(user);
    }

    private String getUsernameIfValid(String newUsername) {
        if (!newUsername.isBlank())
            return newUsername;
        throw new ConstraintViolationException("Username can not be blank.", null);
    }

    private String getEmailIfValid(String email) {
        if (email.matches(".+?@.+?\\..+"))
            return email;
        throw new ConstraintViolationException("Email is not valid.", null);
    }

    private String getPhoneIfValid(String phone) {
        phone = phone.replaceAll("\\s+", "");

        if (phone.matches("\\d{11}")) {
            return phone;
        } else if (phone.matches("\\d\\(\\d{3}\\)\\d{3}-\\d{2}-\\d{2}")) {
            return formatPhone(phone);
        } else if (phone.matches("\\d\\(\\d{3}\\)\\d{7}")) {
            return formatPhone(phone);
        }

        throw new ConstraintViolationException("Phone is not valid.", null);
    }

    private String formatPhone(String phone) {
        phone = phone.replaceAll("\\s+", "");
        phone = phone.replaceAll("\\(", "");
        phone = phone.replaceAll("\\)", "");
        phone = phone.replaceAll("-", "");
        return phone;
    }
}
