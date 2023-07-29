package com.example.electroscoot.controllers;

import com.example.electroscoot.dto.CreateScooterDTO;
import com.example.electroscoot.dto.ScooterDTO;
import com.example.electroscoot.dto.UpdateScooterDTO;
import com.example.electroscoot.services.interfaces.IScooterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.slf4j.Logger;

@RestController
public class ScooterController {
    @Autowired
    private IScooterService scooterService;
    @Autowired
    private Logger logger;

    public ScooterDTO findById(int id) {
        logger.info("The <findById> method is called from Scooter Controller.");
        return scooterService.findById(id);
    }

    public List<ScooterDTO> getList() {
        logger.info("The <getList> method is called from Scooter Controller.");
        return scooterService.getList();
    }

    public ScooterDTO create(CreateScooterDTO createData) {
        logger.info("The <create> method is called from Scooter Controller.");
        return scooterService.create(createData);
    }

    public boolean deleteById(int id) {
        logger.info("The <deleteById> method is called from Scooter Controller.");
        return scooterService.deleteById(id);
    }

    public ScooterDTO updateById(UpdateScooterDTO updateData) {
        logger.info("The <updateById> method is called from Scooter Controller.");
        return scooterService.updateById(updateData);
    }
}
