package com.example.electroscoot.controllers;

import com.example.electroscoot.dto.CreateScooterRentalDTO;
import com.example.electroscoot.dto.RentalPlaceNameDTO;
import com.example.electroscoot.dto.ScooterRentalDTO;
import com.example.electroscoot.services.interfaces.IScooterRentalService;
import com.example.electroscoot.utils.enums.RentalStateEnum;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rentals")
public class ScooterRentalController {
    @Autowired
    private IScooterRentalService scooterRentalService;
    @Autowired
    private Logger logger;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ScooterRentalDTO findById(@PathVariable("id") int id) {
        logger.info("The <findById> method is called from Scooter Rental Controller.");
        return scooterRentalService.findById(id);
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ScooterRentalDTO> getList(@RequestParam(value = "passed-filter", required = false) Boolean passed) {
        logger.info("The <getList> method is called from Scooter Rental Controller. Passed filter: " + passed);
        return scooterRentalService.getList(passed);
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ScooterRentalDTO create(@RequestBody CreateScooterRentalDTO createData) {
        logger.info("The <create> method is called from Scooter Rental Controller.");
        return scooterRentalService.create(createData);
    }

    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ScooterRentalDTO closeRentalById(@PathVariable("id") int id, @RequestBody RentalPlaceNameDTO rentalPlaceNameDTO) {
        logger.info("The <closeRentalById> method is called from Scooter Rental Controller.");
        return scooterRentalService.closeRentalById(id, rentalPlaceNameDTO);
    }
}
