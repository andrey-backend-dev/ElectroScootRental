package com.example.electroscoot.dto;

import com.example.electroscoot.entities.Role;
import lombok.Data;

@Data
public class RoleDTO {
    private int id;
    private String name;

    public RoleDTO(Role role) {
        this.id = role.getId();
        this.name = role.getName();
    }
}
