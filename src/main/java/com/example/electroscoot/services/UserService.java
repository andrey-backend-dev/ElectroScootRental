package com.example.electroscoot.services;

import com.example.electroscoot.dao.RoleRepository;
import com.example.electroscoot.dao.UserRepository;
import com.example.electroscoot.dto.*;
import com.example.electroscoot.entities.Role;
import com.example.electroscoot.entities.User;
import com.example.electroscoot.services.interfaces.IJwtService;
import com.example.electroscoot.services.interfaces.IUserService;
import com.example.electroscoot.utils.enums.UserStatus;
import com.example.electroscoot.utils.mappers.RoleMapper;
import com.example.electroscoot.utils.mappers.ScooterRentalMapper;
import com.example.electroscoot.utils.mappers.UserMapper;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final IJwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final Clock clock;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final ScooterRentalMapper scooterRentalMapper;
    @Value("${business.defaultRole}")
    private String defaultRole;
    @Value("${business.subscriptionCost}")
    private float subscriptionCost;

    @Override
    @Transactional
    public User create(@Valid RegistrationDTO registrationData) {

        Role role = roleRepository.findByName(defaultRole).orElseThrow(() -> {
            return new IllegalArgumentException("The role with name " + defaultRole + " does not exist.");
        });

        User user = userMapper.registrationToUser(registrationData.getUsername(),
                passwordEncoder.encode(registrationData.getPassword()),
                formatPhone(registrationData.getPhone()),
                UserStatus.OK,
                new HashSet<>(Set.of(role)),
                LocalDateTime.now(clock));

        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO findById(@Positive(message = "Id must be more than zero.") int id) {
        return userMapper.userToUserDto(userRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("The user with id " + id + " does not exist.");
        }));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO findByUsername(@NotBlank(message = "Username is mandatory.") String username) {
        return userMapper.userToUserDto(userRepository.findByUsername(username).orElseThrow(() -> {
            return new IllegalArgumentException("The user with username " + username + " does not exist.");
        }));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        return userMapper.userToUserDto(userRepository.findAll());
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
        String phone = updateData.getPhone();
        if (phone != null) {
            user.setPhone(getPhoneIfValid(phone));
        }
        String newUsername = updateData.getUsername();
        if (newUsername != null) {
            user.setUsername(getUsernameIfValid(newUsername));
        }

        return userMapper.userToUserDto(user);
    }

    @Override
    @Transactional
    public UpdateUserResponseDTO updateByPrincipal(String username, UpdateUserDTO updateData) {
        UserDTO userDTO = updateByUsername(username, updateData);
        User user = userRepository.findByUsername(userDTO.getUsername()).orElseThrow(() -> {
            return new IllegalArgumentException("The user with username " + userDTO.getUsername() + " does not exist.");
        });
        String jwt = jwtService.generateJWT(user);
        return new UpdateUserResponseDTO(userDTO, jwt);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<RoleDTO> findRolesByUsername(@NotBlank(message = "Username is mandatory.") String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            return new IllegalArgumentException("The user with username " + username + " does not exist.");
        });

        return roleMapper.roleToRoleDto(user.getRoles());
    }

    @Override
    @Transactional
    public Set<RoleDTO> addRoleByUsername(@NotBlank(message = "Username is mandatory.") String username,
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

        String newJwt = jwtService.generateJWT(user);

        return roleMapper.roleToRoleDto(user.getRoles());
    }

    @Override
    @Transactional
    public Set<RoleDTO> removeRoleByUsername(@NotBlank(message = "Username is mandatory.") String username,
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

        String newJwt = jwtService.generateJWT(user);

        return roleMapper.roleToRoleDto(user.getRoles());
    }

    @Override
    @Transactional
    public UserDTO addMoneyByUsername(@NotBlank(message = "Username is mandatory.") String username,
                                      @Valid MoneyDTO moneyDTO) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            return new IllegalArgumentException("The user with username " + username + " does not exist.");
        });
        user.setMoney(user.getMoney() + moneyDTO.getMoney());
        return userMapper.userToUserDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScooterRentalDTO> findRentHistoryByUsername(@NotBlank(message = "Username is mandatory.") String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            return new IllegalArgumentException("The user with username " + username + " does not exist.");
        });
        return scooterRentalMapper.scooterRentalToScooterRentalDto(user.getScooterRentals());
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

        return userMapper.userToUserDto(user);
    }

    @Override
    @Transactional
    public UserDTO updateUserStatusByUsername(@NotBlank(message = "Username is mandatory.") String username, UserStatus status) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            return new IllegalArgumentException("The user with username " + username + " does not exist.");
        });

        if (status == null) {
            throw new ConstraintViolationException("User status is not valid.", null);
        }

        if (user.getStatus() == status) {
            throw new IllegalArgumentException(String.format("The user with username %s already has %s status.", username, status.getName()));
        }

        user.setStatus(status);

        return userMapper.userToUserDto(user);
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
