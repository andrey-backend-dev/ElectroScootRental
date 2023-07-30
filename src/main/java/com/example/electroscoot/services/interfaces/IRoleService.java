package com.example.electroscoot.services.interfaces;

import com.example.electroscoot.dto.RoleDTO;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public interface IRoleService {
    RoleDTO create(@NotBlank(message = "Name is mandatory.") String name);
    List<RoleDTO> getList();
    boolean doesExistByName(@NotBlank(message = "Name is mandatory.") String name);
}
