package com.example.electroscoot.dto;

import com.example.electroscoot.entities.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoleDTO {
    private int id;
    @NotBlank(message = "Role name is mandatory.")
    private String name;
}
