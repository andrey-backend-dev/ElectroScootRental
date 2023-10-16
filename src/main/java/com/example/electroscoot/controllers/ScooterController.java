package com.example.electroscoot.controllers;

import com.example.electroscoot.dto.ScooterDTO;
import com.example.electroscoot.services.interfaces.IScooterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
}
