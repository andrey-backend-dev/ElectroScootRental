package com.example.electroscoot.services.interfaces;

import com.example.electroscoot.dto.*;
import com.example.electroscoot.entities.User;
import com.example.electroscoot.utils.enums.UserStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.util.List;
import java.util.Set;

public interface IUserService {
    User create(@Valid RegistrationDTO registrationData);
    UserDTO findById(@Positive(message = "Id must be more than zero.") int id);
    UserDTO findByUsername(@NotBlank(message = "Username is mandatory.") String username);
    UserDTO updateByUsername(@NotBlank(message = "Username is mandatory.") String username, UpdateUserDTO updateData);
    UpdateUserResponseDTO updateByPrincipal(@NotBlank(message = "Username is mandatory.") String username, UpdateUserDTO updateData);
    List<UserDTO> findAll();
    boolean deleteByUsername(@NotBlank(message = "Username is mandatory.") String username);
    Set<RoleDTO> findRolesByUsername(@NotBlank(message = "Username is mandatory.") String username);
    Set<RoleDTO> addRoleByUsername(@NotBlank(message = "Username is mandatory.") String username,
                              @Valid RoleNameDTO roleNameDTO);
    Set<RoleDTO> removeRoleByUsername(@NotBlank(message = "Username is mandatory.") String username,
                                       @Valid RoleNameDTO roleNameDTO);
    UserDTO addMoneyByUsername(@NotBlank(message = "Username is mandatory.") String username,
                               @Valid MoneyDTO moneyDTO);
    List<ScooterRentalDTO> findRentHistoryByUsername(@NotBlank(message = "Username is mandatory.") String username);
    UserDTO buySubscriptionByUsername(@NotBlank(message = "Username is mandatory.") String username);
    UserDTO updateUserStatusByUsername(@NotBlank(message = "Username is mandatory.") String username, UserStatus status);
}
