package com.example.electroscoot.services;

import com.example.electroscoot.dao.RoleRepository;
import com.example.electroscoot.dto.RoleDTO;
import com.example.electroscoot.entities.Role;
import com.example.electroscoot.services.interfaces.IRoleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class RoleService implements IRoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public RoleDTO create(@Valid RoleDTO roleDTO) {
        Role role = new Role();

        role.setName(roleDTO.getName());

        return new RoleDTO(roleRepository.save(role));
    }

    @Override
    @Transactional
    public boolean deleteByName(String name) {
        roleRepository.findByName(name).orElseThrow(() -> {
            return new IllegalArgumentException("The role with name " + name + " does not exist.");
        });

        roleRepository.deleteByName(name);

        return roleRepository.findByName(name).orElse(null) == null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleDTO> getList() {
        return ((List<Role>) roleRepository.findAll()).stream().map(RoleDTO::new).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean doesExistByName(@NotBlank(message = "Name is mandatory.") String name) {
        return roleRepository.findByName(name).orElse(null) != null;
    }
}
