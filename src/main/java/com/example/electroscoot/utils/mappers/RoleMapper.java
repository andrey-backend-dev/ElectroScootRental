package com.example.electroscoot.utils.mappers;

import com.example.electroscoot.dto.RoleDTO;
import com.example.electroscoot.entities.Role;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role roleDtoToRole(RoleDTO roleDTO);
    RoleDTO roleToRoleDto(Role role);
    List<RoleDTO> roleToRoleDto(Iterable<Role> roles);
}
