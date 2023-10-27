package com.example.electroscoot.controllers;

import com.example.electroscoot.dto.CreateScooterModelDTO;
import com.example.electroscoot.dto.ScooterDTO;
import com.example.electroscoot.dto.ScooterModelDTO;
import com.example.electroscoot.dto.UpdateScooterModelDTO;
import com.example.electroscoot.services.interfaces.IScooterModelService;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/scooter-models")
public class ScooterModelController {
    private final IScooterModelService scooterModelService;

    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ScooterModelDTO findById(@PathVariable("id") int id) {
        return scooterModelService.findById(id);
    }

    @GetMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ScooterModelDTO findByName(@PathVariable("name") String name) {
        return scooterModelService.findByName(name);
    }

    // methods below require authorities

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ScooterModelDTO> findAllScooterModels() {
        return scooterModelService.findAll();
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ScooterModelDTO createScooterModel(@RequestBody CreateScooterModelDTO createData) {
        return scooterModelService.create(createData);
    }

    @PutMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ScooterModelDTO updateScooterModelByName(@PathVariable("name") String name, @RequestBody UpdateScooterModelDTO updateData) {
        return scooterModelService.updateByName(name, updateData);
    }

    @DeleteMapping(value = "/{name}")
    public boolean deleteScooterModelByName(@PathVariable("name") String name) {
        return scooterModelService.deleteByName(name);
    }

    @GetMapping(value = "/{name}/scooters", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ScooterDTO> getScootersByScooterModelName(@PathVariable("name") String name) {
        return scooterModelService.findScootersByName(name);
    }
}
