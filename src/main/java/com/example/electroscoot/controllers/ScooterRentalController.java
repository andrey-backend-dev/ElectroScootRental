package com.example.electroscoot.controllers;

import com.example.electroscoot.dto.CreateScooterRentalDTO;
import com.example.electroscoot.dto.ScooterRentalDTO;
import com.example.electroscoot.services.interfaces.IScooterRentalService;
import com.example.electroscoot.utils.enums.RentalStateEnum;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rentals")
public class ScooterRentalController {
    @Autowired
    private IScooterRentalService scooterRentalService;
    @Autowired
    private Logger logger;

    public ScooterRentalDTO findById(int id) {
        logger.info("The <findById> method is called from Scooter Rental Controller.");
        return scooterRentalService.findById(id);
    }

    @GetMapping(value = "/")
    public List<ScooterRentalDTO> getList() {
        logger.info("The <getList> method is called from Scooter Rental Controller.");
        return scooterRentalService.getList();
    }

    public ScooterRentalDTO create(CreateScooterRentalDTO createData) {
        logger.info("The <create> method is called from Scooter Rental Controller.");
        return scooterRentalService.create(createData);
    }

    public ScooterRentalDTO closeRentalById(int id, String rentalPlaceName) {
        logger.info("The <closeRentalById> method is called from Scooter Rental Controller.");
        return scooterRentalService.closeRentalById(id, rentalPlaceName);
    }
}
