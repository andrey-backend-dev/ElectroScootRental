package com.example.electroscoot.controllers;

import com.example.electroscoot.dto.RoleDTO;
import com.example.electroscoot.services.interfaces.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.slf4j.Logger;

@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private IRoleService roleService;
    @Autowired
    private Logger logger;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public RoleDTO create(@RequestBody RoleDTO roleDTO) {
        logger.info("The <create> method is called from Role Controller.");
        return roleService.create(roleDTO);
    }

    @DeleteMapping(value = "/{name}")
    public boolean deleteByName(@PathVariable("name") String name) {
        logger.info("The <delete> method is called from Role Controller.");
        return roleService.deleteByName(name);
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RoleDTO> getList() {
        logger.info("The <getList> method is called from Role Controller.");
        return roleService.getList();
    }

    @GetMapping("/exists")
    public boolean doesExistByName(@RequestParam("name") String name) {
        logger.info("The <doesExistByName> method is called from Role Controller.");
        return roleService.doesExistByName(name);
    }
}
