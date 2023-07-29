package com.example.electroscoot.dto;

import com.example.electroscoot.infra.validation.annotations.Phone;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RegistrationDTO {
    @NotBlank(message = "Username is mandatory.")
    private String username;
    @NotBlank(message = "Password is mandatory.")
    private String password;
    @Phone(message = "Phone format is invalid.")
    private String phone;
}
