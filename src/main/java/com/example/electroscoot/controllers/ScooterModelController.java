package com.example.electroscoot.controllers;

import com.example.electroscoot.dto.CreateScooterModelDTO;
import com.example.electroscoot.dto.ScooterDTO;
import com.example.electroscoot.dto.ScooterModelDTO;
import com.example.electroscoot.dto.UpdateScooterModelDTO;
import com.example.electroscoot.services.interfaces.IScooterModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.slf4j.Logger;

@RestController
public class ScooterModelController {
    @Autowired
    private IScooterModelService scooterModelService;
    @Autowired
    private Logger logger;

    public ScooterModelDTO findById(int id) {
        logger.info("The <findById> method is called from Scooter Model Controller.");
        return scooterModelService.findById(id);
    }

    public ScooterModelDTO findByName(String name) {
        logger.info("The <findByName> method is called from Scooter Model Controller.");
        return scooterModelService.findByName(name);
    }

    public List<ScooterModelDTO> getList() {
        logger.info("The <getList> method is called from Scooter Model Controller.");
        return scooterModelService.getList();
    }

    public ScooterModelDTO create(CreateScooterModelDTO createData) {
        logger.info("The <create> method is called from Scooter Model Controller.");
        return scooterModelService.create(createData);
    }

    public ScooterModelDTO updateByName(String name, UpdateScooterModelDTO updateData) {
        logger.info("The <updateByName> method is called from Scooter Model Controller.");
        return scooterModelService.updateByName(name, updateData);
    }

    public boolean deleteByName(String name) {
        logger.info("The <deleteByName> method is called from Scooter Model Controller.");
        return scooterModelService.deleteByName(name);
    }

    public List<ScooterDTO> getScootersByName(String name) {
        logger.info("The <getScootersByName> method is called from Scooter Model Controller.");
        return scooterModelService.getScootersByName(name);
    }
}
