package com.example.electroscoot.utils.mappers;

import com.example.electroscoot.dto.RoleDTO;
import com.example.electroscoot.entities.Authority;
import com.example.electroscoot.entities.Role;
import org.mapstruct.Mapper;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class RoleMapper {
    public Role roleDtoToRole(RoleDTO roleDTO) {
        Role role = new Role();
        role.setName(roleDTO.getName());
        role.setAuthorities(roleDTO.getAuthorities().stream().map(Authority::new).collect(Collectors.toSet()));
        return role;
    }
    public RoleDTO roleToRoleDto(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName(role.getName());
        roleDTO.setAuthorities(role.getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet()));
        return roleDTO;
    }
    public abstract Set<RoleDTO> roleToRoleDto(Iterable<Role> roles);
}
