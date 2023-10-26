package com.example.electroscoot.controllers;

import com.example.electroscoot.dto.AuthoritiesDTO;
import com.example.electroscoot.dto.RoleDTO;
import com.example.electroscoot.services.interfaces.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/roles")
public class RoleController {
    private final IRoleService roleService;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public RoleDTO createRole(@RequestBody RoleDTO roleDTO) {
        return roleService.create(roleDTO);
    }

    @DeleteMapping(value = "/{name}")
    public boolean deleteRoleByName(@PathVariable("name") String name) {
        return roleService.deleteByName(name);
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<RoleDTO> findAllRoles() {
        return roleService.findAll();
    }

    @GetMapping(value = "/{name}")
    public RoleDTO findByName(@PathVariable("name") String name) {
        return roleService.findRoleByName(name);
    }

    @PatchMapping(value = "/{name}/add-authorities")
    public RoleDTO addAuthoritiesByName(@PathVariable("name") String name, @RequestBody AuthoritiesDTO dto) {
        return roleService.addAuthoritiesByName(name, dto.getAuthorities());
    }

    @PatchMapping(value = "/{name}/remove-authorities")
    public RoleDTO removeAuthoritiesByName(@PathVariable("name") String name, @RequestBody AuthoritiesDTO dto) {
        return roleService.removeAuthoritiesByName(name, dto.getAuthorities());
    }
}
