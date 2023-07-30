package com.example.electroscoot.controllers;

import com.example.electroscoot.dto.CreateRentalPlaceDTO;
import com.example.electroscoot.dto.RentalPlaceDTO;
import com.example.electroscoot.dto.ScooterDTO;
import com.example.electroscoot.dto.UpdateRentalPlaceDTO;
import com.example.electroscoot.services.interfaces.IRentalPlaceService;
import com.example.electroscoot.utils.maps.SortMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.slf4j.Logger;

@RestController
@RequestMapping("/rental-places")
public class RentalPlaceController {
    @Autowired
    private IRentalPlaceService rentalPlaceService;
    @Autowired
    private Logger logger;

    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RentalPlaceDTO findById(@PathVariable("id") int id) {
        logger.info("The <findById> method is called from Rental Place Controller.");
        return rentalPlaceService.findById(id);
    }

    @GetMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RentalPlaceDTO findByName(@PathVariable("name") String name) {
        logger.info("The <findByName> method is called from Rental Place Controller.");
        return rentalPlaceService.findByName(name);
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RentalPlaceDTO> getList(@RequestParam("sort") String sortMethod, @RequestParam("city") String city) {
        logger.info("The <getList> method is called from Rental Place Controller. Sort: " + sortMethod + ", byCity: " + city);
        return rentalPlaceService.getList(SortMap.getSortByName(sortMethod), city);
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public RentalPlaceDTO create(@RequestBody CreateRentalPlaceDTO createData) {
        logger.info("The <create> method is called from Rental Place Controller.");
        return rentalPlaceService.create(createData);
    }

    @PutMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public RentalPlaceDTO updateByName(@PathVariable("name") String name, @RequestBody UpdateRentalPlaceDTO updateData) {
        logger.info("The <updateByName> method is called from Rental Place Controller.");
        return rentalPlaceService.updateByName(name, updateData);
    }

    @DeleteMapping(value = "/{name}")
    public boolean deleteByName(@PathVariable("name") String name) {
        logger.info("The <deleteByName> method is called from Rental Place Controller.");
        return rentalPlaceService.deleteByName(name);
    }

    @GetMapping(value = "/{name}/scooters", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ScooterDTO> getScootersByName(@PathVariable("name") String name) {
        logger.info("The <getScootersByName> method is called from Rental Place Controller.");
        return rentalPlaceService.getScootersByName(name);
    }
}
