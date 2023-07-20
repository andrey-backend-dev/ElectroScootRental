package com.example.electroscoot.services.interfaces;

import com.example.electroscoot.dto.RegistrationDTO;
import com.example.electroscoot.dto.UpdateUserDTO;
import com.example.electroscoot.dto.UserDTO;
import com.example.electroscoot.entities.Role;

import java.util.List;

public interface IUserService {
    UserDTO register(RegistrationDTO registrationData);
    UserDTO findById(int id);
    UserDTO findByUsername(String username);
    UserDTO updateByUsername(String username, UpdateUserDTO updateData);
    List<UserDTO> getList();
    boolean deleteByUsername(String username);
    void addRoleByUsername(String username, Role role);
    void addMoneyByUsername(String username, float money);
    void getRentHistoryByUsername(String username);
}
