package com.example.electroscoot.controllers;

import com.example.electroscoot.dto.CreateRentalPlaceDTO;
import com.example.electroscoot.dto.RentalPlaceDTO;
import com.example.electroscoot.dto.ScooterDTO;
import com.example.electroscoot.dto.UpdateRentalPlaceDTO;
import com.example.electroscoot.services.interfaces.IRentalPlaceService;
import com.example.electroscoot.utils.maps.SortMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.slf4j.Logger;

@RestController
public class RentalPlaceController {
    @Autowired
    private IRentalPlaceService rentalPlaceService;
    @Autowired
    private Logger logger;

    public RentalPlaceDTO findById(int id) {
        logger.info("The <findById> method is called from Rental Place Controller.");
        return rentalPlaceService.findById(id);
    }

    public RentalPlaceDTO findByName(String name) {
        logger.info("The <findByName> method is called from Rental Place Controller.");
        return rentalPlaceService.findByName(name);
    }

    public List<RentalPlaceDTO> getList(String sortMethod) {
        logger.info("The <getList> method is called from Rental Place Controller.");
        return rentalPlaceService.getList(SortMap.getSortByName(sortMethod));
    }

    public List<RentalPlaceDTO> getListByCity(String city) {
        logger.info("The <getListByCity> method is called from Rental Place Controller.");
        return rentalPlaceService.getListByCity(city);
    }

    public RentalPlaceDTO create(CreateRentalPlaceDTO createData) {
        logger.info("The <create> method is called from Rental Place Controller.");
        return rentalPlaceService.create(createData);
    }

    public RentalPlaceDTO updateByName(String name, UpdateRentalPlaceDTO updateData) {
        logger.info("The <updateByName> method is called from Rental Place Controller.");
        return rentalPlaceService.updateByName(name, updateData);
    }

    public boolean deleteByName(String name) {
        logger.info("The <deleteByName> method is called from Rental Place Controller.");
        return rentalPlaceService.deleteByName(name);
    }

    public List<ScooterDTO> getScootersByName(String name) {
        logger.info("The <getScootersByName> method is called from Rental Place Controller.");
        return rentalPlaceService.getScootersByName(name);
    }
}
