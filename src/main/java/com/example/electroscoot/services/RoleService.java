package com.example.electroscoot.services;

import com.example.electroscoot.dao.RoleRepository;
import com.example.electroscoot.dto.RoleDTO;
import com.example.electroscoot.entities.Role;
import com.example.electroscoot.services.interfaces.IRoleService;
import com.example.electroscoot.utils.mappers.RoleMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RoleService implements IRoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper mapper;

    @Override
    @Transactional
    public RoleDTO create(@Valid RoleDTO roleDTO) {
        Role role = mapper.roleDtoToRole(roleDTO);

        return mapper.roleToRoleDto(roleRepository.save(role));
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
        return mapper.roleToRoleDto(roleRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean doesExistByName(@NotBlank(message = "Name is mandatory.") String name) {
        return roleRepository.findByName(name).orElse(null) != null;
    }
}
