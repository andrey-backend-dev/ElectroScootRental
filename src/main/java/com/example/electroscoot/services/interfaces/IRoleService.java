package com.example.electroscoot.services.interfaces;

import com.example.electroscoot.dto.RoleDTO;

import java.util.List;

public interface IRoleService {
    RoleDTO create(String name);
    List<RoleDTO> getList();
    boolean doesExistByName(String name);
}
