package com.example.electroscoot.controllers;

import com.example.electroscoot.dto.RentalPlaceDTO;
import com.example.electroscoot.dto.ScooterDTO;
import com.example.electroscoot.services.interfaces.IRentalPlaceService;
import com.example.electroscoot.utils.enums.OrderEnum;
import com.example.electroscoot.utils.enums.SortMethod;
import com.example.electroscoot.utils.mappers.OrderEnumMapper;
import com.example.electroscoot.utils.mappers.SortEnumMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public List<RentalPlaceDTO> getList(@RequestParam(value = "sort", required = false) String sortMethod,
                                        @RequestParam(value = "ordering", required = false) String ordering,
                                        @RequestParam(value = "city", required = false) String city) {
        logger.info("The <getList> method is called from Rental Place Controller. " +
                "Sort: " + sortMethod + ", ordering: " + ordering + ", byCity: " + city);
        SortMethod sort = sortMethod == null || SortEnumMapper.getSortByName(sortMethod) == SortMethod.NULL
                ? SortMethod.NULL
                : SortEnumMapper.getSortByName(sortMethod);
        OrderEnum order = ordering == null || OrderEnumMapper.getOrderingByName(ordering) == OrderEnum.NULL
                ? OrderEnum.NULL
                : OrderEnumMapper.getOrderingByName(ordering);
        return rentalPlaceService.getList(sort, order, city);
    }

    @GetMapping(value = "/{name}/scooters", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ScooterDTO> getScootersByName(@PathVariable("name") String name) {
        logger.info("The <getScootersByName> method is called from Rental Place Controller.");
        return rentalPlaceService.getScootersByName(name);
    }
}
