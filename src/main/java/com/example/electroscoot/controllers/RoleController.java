package com.example.electroscoot.controllers;

import com.example.electroscoot.dto.RoleDTO;
import com.example.electroscoot.services.interfaces.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.slf4j.Logger;

@RestController
public class RoleController {
    @Autowired
    private IRoleService roleService;
    @Autowired
    private Logger logger;

    public RoleDTO create(String name) {
        logger.info("The <create> method is called from Role Controller.");
        return roleService.create(name);
    }

    public List<RoleDTO> getList() {
        logger.info("The <getList> method is called from Role Controller.");
        return roleService.getList();
    }

    public boolean doesExistByName(String name) {
        logger.info("The <doesExistByName> method is called from Role Controller.");
        return roleService.doesExistByName(name);
    }
}
