package com.example.electroscoot.services;

import com.example.electroscoot.dao.RoleRepository;
import com.example.electroscoot.entities.Role;
import com.example.electroscoot.services.interfaces.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService implements IRoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public Role create(String name) {
        Role role = new Role();

        role.setName(name);

        return roleRepository.save(role);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> getList() {
        return (List<Role>) roleRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}
