package com.example.electroscoot.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoleNameDTO {
    @NotBlank(message = "Role name is mandatory.")
    private String name;
}
