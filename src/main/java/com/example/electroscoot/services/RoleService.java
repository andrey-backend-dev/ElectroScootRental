package com.example.electroscoot.services;

import com.example.electroscoot.dao.RoleRepository;
import com.example.electroscoot.dto.RoleDTO;
import com.example.electroscoot.entities.Role;
import com.example.electroscoot.services.interfaces.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleService implements IRoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public RoleDTO create(String name) {
        Role role = new Role();

        role.setName(name);

        return new RoleDTO(roleRepository.save(role));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleDTO> getList() {
        return ((List<Role>) roleRepository.findAll()).stream().map(RoleDTO::new).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean doesExistByName(String name) {
        return roleRepository.findByName(name).orElse(null) != null;
    }
}
