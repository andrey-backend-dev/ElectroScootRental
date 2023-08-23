package com.example.electroscoot.dto;

import lombok.Data;

import java.util.List;

@Data
public class RoleJWTResponseDTO {
    private List<RoleDTO> roles;
    private String newJWT;

    public RoleJWTResponseDTO(List<RoleDTO> roles, String newJWT) {
        this.roles = roles;
        this.newJWT = newJWT;
    }
}
