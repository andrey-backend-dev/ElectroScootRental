package com.example.electroscoot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class AuthenticationDTO {
    private UserDTO userDTO;
    private String jwt;
}

