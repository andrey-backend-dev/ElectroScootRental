package com.example.electroscoot.dto;

import com.example.electroscoot.entities.User;
import com.example.electroscoot.utils.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UpdateUserResponseDTO {
    private UserDTO userDTO;
    private String jwt;
}
