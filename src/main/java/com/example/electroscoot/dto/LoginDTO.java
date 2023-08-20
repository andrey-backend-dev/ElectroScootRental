package com.example.electroscoot.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class LoginDTO {
    @NotBlank(message = "Username is mandatory.")
    private String username;
    @NotBlank(message = "Password is mandatory.")
    private String password;
}
