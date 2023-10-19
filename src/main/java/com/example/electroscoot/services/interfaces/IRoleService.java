package com.example.electroscoot.services.interfaces;

import com.example.electroscoot.dto.RoleDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public interface IRoleService {
    RoleDTO create(@Valid RoleDTO roleDTO);
    boolean deleteByName(String name);
    List<RoleDTO> findAll();
    boolean doesExistByName(@NotBlank(message = "Name is mandatory.") String name);
}
