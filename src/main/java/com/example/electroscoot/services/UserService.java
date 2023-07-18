package com.example.electroscoot.services;

import com.example.electroscoot.dao.RoleRepository;
import com.example.electroscoot.dao.UserRepository;
import com.example.electroscoot.dto.RegistrationDTO;
import com.example.electroscoot.dto.UpdateUserDTO;
import com.example.electroscoot.dto.UserDTO;
import com.example.electroscoot.entities.Role;
import com.example.electroscoot.entities.User;
import com.example.electroscoot.services.interfaces.IRoleService;
import com.example.electroscoot.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Value("${business.defaultRole}")
    private String defaultRole;

    @Override
    @Transactional
    public UserDTO register(RegistrationDTO registrationData) {

        User user = new User();
        Role role = roleRepository.findByName(defaultRole);

        user.setPassword(registrationData.getPassword());
        user.setPhone(registrationData.getPhone());
        user.setUsername(registrationData.getUsername());
        user.setRoles(new HashSet<>(Set.of(role)));

        return new UserDTO(userRepository.save(user));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO findById(int id) {
        return new UserDTO(userRepository.findById(id).orElse(null));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO findByUsername(String username) {
        return new UserDTO(userRepository.findByUsername(username));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getList() {
        return ((List<User>) userRepository.findAll()).stream().map(UserDTO::new).toList();
    }

    @Override
    @Transactional
    public boolean deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
        return true;
    }

    @Override
    @Transactional
    public UserDTO updateByUsername(String username, UpdateUserDTO updateData) {
        User user = userRepository.findByUsername(username);

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
            user.setEmail(email);
        }

        String newUsername = updateData.getUsername();
        if (newUsername != null) {
            user.setUsername(newUsername);
        }

        String phone = updateData.getPhone();
        if (phone != null) {
            user.setPhone(phone);
        }

        return new UserDTO(user);
    }

    @Override
    @Transactional
    public void addRoleByUsername(String username, Role role) {

    }

    @Override
    @Transactional
    public void addMoneyByUsername(String username, float money) {

    }

    @Override
    @Transactional
    public void getRentHistoryByUsername(String username) {

    }
}
