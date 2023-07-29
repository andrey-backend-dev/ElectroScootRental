package com.example.electroscoot.services;

import com.example.electroscoot.dao.RoleRepository;
import com.example.electroscoot.dao.UserRepository;
import com.example.electroscoot.dto.RegistrationDTO;
import com.example.electroscoot.dto.RoleDTO;
import com.example.electroscoot.dto.ScooterRentalDTO;
import com.example.electroscoot.dto.UpdateUserDTO;
import com.example.electroscoot.dto.UserDTO;
import com.example.electroscoot.entities.Role;
import com.example.electroscoot.entities.User;
import com.example.electroscoot.infra.validation.validators.PhoneValidator;
import com.example.electroscoot.services.interfaces.IUserService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private Clock clock;
    @Value("${business.defaultRole}")
    private String defaultRole;
    @Value("${business.subscriptionCost}")
    private float subscriptionCost;

    @Override
    @Transactional
    public UserDTO register(@Valid RegistrationDTO registrationData) {

        User user = new User();
        Role role = roleRepository.findByName(defaultRole).orElseThrow(() -> {
            return new IllegalArgumentException("The role with name " + defaultRole + " does not exist.");
        });

        user.setPassword(registrationData.getPassword());
        user.setPhone(formatPhone(registrationData.getPhone()));
        user.setUsername(registrationData.getUsername());
        user.setRoles(new HashSet<>(Set.of(role)));
        user.setRegisteredSince(LocalDateTime.now(clock));

        return new UserDTO(userRepository.save(user));
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
    public UserDTO addRoleByUsername(@NotBlank(message = "Username is mandatory.") String username,
                                     @NotBlank(message = "Role name is mandatory.") String roleName) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            return new IllegalArgumentException("The user with username " + username + " does not exist.");
        });
        Set<Role> roles = user.getRoles();
        roles.add(roleRepository.findByName(roleName).orElseThrow(() -> {
            return new IllegalArgumentException("The role with name " + defaultRole + " does not exist.");
        }));
        return new UserDTO(user);
    }

    @Override
    @Transactional
    public UserDTO removeRoleByUsername(@NotBlank(message = "Username is mandatory.") String username,
                                        @NotBlank(message = "Role name is mandatory.") String roleName) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            return new IllegalArgumentException("The user with username " + username + " does not exist.");
        });
        Set<Role> roles = user.getRoles();
        roles.remove(roleRepository.findByName(roleName).orElseThrow(() -> {
            return new IllegalArgumentException("The role with name " + roleName + " does not exist.");
        }));
        user.setRoles(roles);
        return new UserDTO(user);
    }

    @Override
    @Transactional
    public UserDTO addMoneyByUsername(@NotBlank(message = "Username is mandatory.") String username,
                                      @Positive(message = "Money must be more than zero.") float money) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            return new IllegalArgumentException("The user with username " + username + " does not exist.");
        });
        user.setMoney(user.getMoney() + money);
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

        float money = user.getMoney();
        if (money >= subscriptionCost) {
            user.setMoney(money - subscriptionCost);
            user.setSubscriptionTill(LocalDateTime.now(clock).plusMonths(1));
        } else {
            throw new IllegalArgumentException(user.getUsername() + " does not have enough money to buy subscription.");
        }

        return new UserDTO(user);
    }

    private String getUsernameIfValid(String newUsername) {
        if (!newUsername.isBlank())
            return newUsername;
        throw new ConstraintViolationException("Username is not valid.", null);
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
