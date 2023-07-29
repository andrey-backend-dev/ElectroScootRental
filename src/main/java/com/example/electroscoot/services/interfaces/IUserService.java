package com.example.electroscoot.services.interfaces;

import com.example.electroscoot.dto.RegistrationDTO;
import com.example.electroscoot.dto.RoleDTO;
import com.example.electroscoot.dto.ScooterRentalDTO;
import com.example.electroscoot.dto.UpdateUserDTO;
import com.example.electroscoot.dto.UserDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.util.List;

public interface IUserService {
    UserDTO register(@Valid RegistrationDTO registrationData);
    UserDTO findById(@Positive(message = "Id must be more than zero.") int id);
    UserDTO findByUsername(@NotBlank(message = "Username is mandatory.") String username);
    UserDTO updateByUsername(@NotBlank(message = "Username is mandatory.") String username, UpdateUserDTO updateData);
    List<UserDTO> getList();
    boolean deleteByUsername(@NotBlank(message = "Username is mandatory.") String username);
    List<RoleDTO> getRolesByUsername(@NotBlank(message = "Username is mandatory.") String username);
    UserDTO addRoleByUsername(@NotBlank(message = "Username is mandatory.") String username,
                              @NotBlank(message = "Role name is mandatory.") String roleName);
    UserDTO removeRoleByUsername(@NotBlank(message = "Username is mandatory.") String username,
                                 @NotBlank(message = "Role name is mandatory.") String roleName);
    UserDTO addMoneyByUsername(@NotBlank(message = "Username is mandatory.") String username,
                               @Positive(message = "Money must be more than zero.") float money);
    List<ScooterRentalDTO> getRentHistoryByUsername(@NotBlank(message = "Username is mandatory.") String username);
    UserDTO buySubscriptionByUsername(@NotBlank(message = "Username is mandatory.") String username);
}
