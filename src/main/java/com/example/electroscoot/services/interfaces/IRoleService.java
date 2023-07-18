package com.example.electroscoot.services.interfaces;

import com.example.electroscoot.entities.Role;

import java.util.List;

public interface IRoleService {
    Role create(String name);
    List<Role> getList();
    Role findByName(String name);
}
