package com.example.electroscoot.controllers;

import com.example.electroscoot.dto.CreateScooterDTO;
import com.example.electroscoot.dto.ScooterDTO;
import com.example.electroscoot.dto.UpdateScooterDTO;
import com.example.electroscoot.services.interfaces.IScooterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.slf4j.Logger;

@RestController
@RequestMapping("/scooters")
public class ScooterController {
    @Autowired
    private IScooterService scooterService;
    @Autowired
    private Logger logger;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ScooterDTO findById(@PathVariable("id") int id) {
        logger.info("The <findById> method is called from Scooter Controller.");
        return scooterService.findById(id);
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ScooterDTO> getList() {
        logger.info("The <getList> method is called from Scooter Controller.");
        return scooterService.getList();
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ScooterDTO create(@RequestBody CreateScooterDTO createData) {
        logger.info("The <create> method is called from Scooter Controller.");
        return scooterService.create(createData);
    }

    @DeleteMapping(value = "/{id}")
    public boolean deleteById(@PathVariable("id") int id) {
        logger.info("The <deleteById> method is called from Scooter Controller.");
        return scooterService.deleteById(id);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ScooterDTO updateById(@RequestBody UpdateScooterDTO updateData) {
        logger.info("The <updateById> method is called from Scooter Controller.");
        return scooterService.updateById(updateData);
    }
}
