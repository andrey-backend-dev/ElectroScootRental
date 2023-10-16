package com.example.electroscoot.services.interfaces;

import com.example.electroscoot.dto.*;
import com.example.electroscoot.entities.User;
import com.example.electroscoot.utils.enums.UserStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.util.List;

public interface IUserService {
    User create(@Valid RegistrationDTO registrationData);
    UserDTO findById(@Positive(message = "Id must be more than zero.") int id);
    UserDTO findByUsername(@NotBlank(message = "Username is mandatory.") String username);
    User updateByUsername(@NotBlank(message = "Username is mandatory.") String username, UpdateUserDTO updateData);
    UpdateUserResponseDTO updateByToken(@NotBlank(message = "Token is mandatory.") String token, UpdateUserDTO updateData);
    List<UserDTO> getList();
    boolean deleteByUsername(@NotBlank(message = "Username is mandatory.") String username);
    boolean deleteByToken(@NotBlank(message = "Token is mandatory.") String token);
    List<RoleDTO> getRolesByUsername(@NotBlank(message = "Username is mandatory.") String username);
    RoleJWTResponseDTO addRoleByUsername(@NotBlank(message = "Username is mandatory.") String username,
                              @Valid RoleNameDTO roleNameDTO);
    RoleJWTResponseDTO removeRoleByUsername(@NotBlank(message = "Username is mandatory.") String username,
                                       @Valid RoleNameDTO roleNameDTO);
    UserDTO addMoneyByUsername(@NotBlank(message = "Username is mandatory.") String username,
                               @Valid MoneyDTO moneyDTO);
    List<ScooterRentalDTO> getRentHistoryByUsername(@NotBlank(message = "Username is mandatory.") String username);
    UserDTO buySubscriptionByUsername(@NotBlank(message = "Username is mandatory.") String username);
    UserDTO changeUserStatusByUsername(@NotBlank(message = "Username is mandatory.") String username, UserStatus status);
}
