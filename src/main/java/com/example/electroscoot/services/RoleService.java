package com.example.electroscoot.services;

import com.example.electroscoot.dao.AuthorityRepository;
import com.example.electroscoot.dao.RoleRepository;
import com.example.electroscoot.dto.RoleDTO;
import com.example.electroscoot.entities.Authority;
import com.example.electroscoot.entities.Role;
import com.example.electroscoot.services.interfaces.IRoleService;
import com.example.electroscoot.utils.mappers.RoleMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class RoleService implements IRoleService {
    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;
    private final RoleMapper mapper;

    @Override
    @Transactional
    public RoleDTO create(@Valid RoleDTO roleDTO) {
        if (roleRepository.findByName(roleDTO.getName()).orElse(null) != null) {
            throw new DataIntegrityViolationException("The role with name " + roleDTO.getName() + " already exists.");
        }

        Role role = mapper.roleDtoToRole(roleDTO);

        return mapper.roleToRoleDto(roleRepository.save(role));
    }

    @Override
    @Transactional
    public boolean deleteByName(@NotBlank(message = "Name is mandatory.") String name) {
        roleRepository.findByName(name).orElseThrow(() -> {
            return new IllegalArgumentException("The role with name " + name + " does not exist.");
        });

        roleRepository.deleteByName(name);

        return roleRepository.findByName(name).orElse(null) == null;
    }

    @Override
    @Transactional
    public RoleDTO addAuthoritiesByName(@NotBlank(message = "Name is mandatory.") String name,
                                                @NotBlank(message = "Authorities is mandatory.") Set<String> authorities) {
        Role role = roleRepository.findByName(name).orElseThrow(() -> {
            return new IllegalArgumentException("The role with name " + name + " does not exist.");
        });

        Set<Authority> initAuthorities = role.getAuthorities();

        for (String authorityName : authorities) {
            Authority authority = authorityRepository.findByName(authorityName).orElseThrow(() -> {
                return new IllegalArgumentException("The authority with name " + authorityName + " does not exist.");
            });

            initAuthorities.add(authority);
        }

        return mapper.roleToRoleDto(role);
    }

    @Override
    @Transactional
    public RoleDTO removeAuthoritiesByName(@NotBlank(message = "Name is mandatory.") String name,
                                                  @NotBlank(message = "Authorities is mandatory.") Set<String> authorities) {
        Role role = roleRepository.findByName(name).orElseThrow(() -> {
            return new IllegalArgumentException("The role with name " + name + " does not exist.");
        });

        Set<Authority> initAuthorities = role.getAuthorities();

        for (String authorityName : authorities) {
            Authority authority = authorityRepository.findByName(authorityName).orElseThrow(() -> {
                return new IllegalArgumentException("The authority with name " + authorityName + " does not exist.");
            });

            initAuthorities.remove(authority);
        }

        return mapper.roleToRoleDto(role);
    }

    @Override
    @Transactional(readOnly = true)
    public RoleDTO findRoleByName(@NotBlank(message = "Name is mandatory.") String name) {
        return mapper.roleToRoleDto(roleRepository.findByName(name).orElseThrow(() -> {
            return new IllegalArgumentException("The role with name " + name + " does not exist.");
        }));
    }

    @Override
    @Transactional(readOnly = true)
    public Set<RoleDTO> findAll() {
        return mapper.roleToRoleDto(roleRepository.findAll());
    }
}
