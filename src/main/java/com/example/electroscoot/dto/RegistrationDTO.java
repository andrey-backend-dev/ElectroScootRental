package com.example.electroscoot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RegistrationDTO {
    private String username;
    private String password;
    private String phone;
}
