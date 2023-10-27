package com.example.electroscoot.services.interfaces;

import com.example.electroscoot.dto.RoleDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public interface IRoleService {
    RoleDTO create(@Valid RoleDTO roleDTO);
    boolean deleteByName(@NotBlank(message = "Name is mandatory.") String name);
    RoleDTO addAuthoritiesByName(@NotBlank(message = "Name is mandatory.") String name,
                                         @NotBlank(message = "Authorities is mandatory.") Set<String> authorities);
    RoleDTO removeAuthoritiesByName(@NotBlank(message = "Name is mandatory.") String name,
                                            @NotBlank(message = "Authorities is mandatory.") Set<String> authorities);
    RoleDTO findRoleByName(@NotBlank(message = "Name is mandatory.") String name);
    Set<RoleDTO> findAll();
}
