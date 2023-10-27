package com.example.electroscoot.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class RoleDTO {
    @NotBlank(message = "Role name is mandatory.")
    private String name;
    private Set<String> authorities = new HashSet<>();
}
