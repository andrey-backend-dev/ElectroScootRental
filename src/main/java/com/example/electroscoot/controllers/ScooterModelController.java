package com.example.electroscoot.controllers;

import com.example.electroscoot.dto.CreateScooterModelDTO;
import com.example.electroscoot.dto.ScooterDTO;
import com.example.electroscoot.dto.ScooterModelDTO;
import com.example.electroscoot.dto.UpdateScooterModelDTO;
import com.example.electroscoot.services.interfaces.IScooterModelService;
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
@RequestMapping("/scooter-models")
public class ScooterModelController {
    @Autowired
    private IScooterModelService scooterModelService;
    @Autowired
    private Logger logger;

    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ScooterModelDTO findById(@PathVariable("id") int id) {
        logger.info("The <findById> method is called from Scooter Model Controller.");
        return scooterModelService.findById(id);
    }

    @GetMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ScooterModelDTO findByName(@PathVariable("name") String name) {
        logger.info("The <findByName> method is called from Scooter Model Controller.");
        return scooterModelService.findByName(name);
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ScooterModelDTO> getList() {
        logger.info("The <getList> method is called from Scooter Model Controller.");
        return scooterModelService.getList();
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ScooterModelDTO create(@RequestBody CreateScooterModelDTO createData) {
        logger.info("The <create> method is called from Scooter Model Controller.");
        return scooterModelService.create(createData);
    }

    @PutMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ScooterModelDTO updateByName(@PathVariable("name") String name, @RequestBody UpdateScooterModelDTO updateData) {
        logger.info("The <updateByName> method is called from Scooter Model Controller.");
        return scooterModelService.updateByName(name, updateData);
    }

    @DeleteMapping(value = "/{name}")
    public boolean deleteByName(@PathVariable("name") String name) {
        logger.info("The <deleteByName> method is called from Scooter Model Controller.");
        return scooterModelService.deleteByName(name);
    }

    @GetMapping(value = "/{name}/scooters", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ScooterDTO> getScootersByName(@PathVariable("name") String name) {
        logger.info("The <getScootersByName> method is called from Scooter Model Controller.");
        return scooterModelService.getScootersByName(name);
    }
}
