package com.example.electroscoot.controllers;

import com.example.electroscoot.dto.CreateRentalPlaceDTO;
import com.example.electroscoot.dto.RentalPlaceDTO;
import com.example.electroscoot.dto.ScooterDTO;
import com.example.electroscoot.dto.UpdateRentalPlaceDTO;
import com.example.electroscoot.services.interfaces.IRentalPlaceService;
import com.example.electroscoot.utils.enums.OrderEnum;
import com.example.electroscoot.utils.enums.SortMethod;
import com.example.electroscoot.utils.mappers.OrderEnumMapper;
import com.example.electroscoot.utils.mappers.SortEnumMapper;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/rental-places")
public class RentalPlaceController {
    private final IRentalPlaceService rentalPlaceService;

    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RentalPlaceDTO findById(@PathVariable("id") int id) {
        return rentalPlaceService.findById(id);
    }

    @GetMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RentalPlaceDTO findByName(@PathVariable("name") String name) {
        return rentalPlaceService.findByName(name);
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RentalPlaceDTO> findAll(@RequestParam(value = "sort", required = false) String sortMethod,
                                        @RequestParam(value = "ordering", required = false) String ordering,
                                        @RequestParam(value = "city", required = false) String city) {
        SortMethod sort = sortMethod == null || SortEnumMapper.getSortByName(sortMethod) == SortMethod.NULL
                ? SortMethod.NULL
                : SortEnumMapper.getSortByName(sortMethod);
        OrderEnum order = ordering == null || OrderEnumMapper.getOrderingByName(ordering) == OrderEnum.NULL
                ? OrderEnum.NULL
                : OrderEnumMapper.getOrderingByName(ordering);
        return rentalPlaceService.findAll(sort, order, city);
    }

    @GetMapping(value = "/{name}/scooters", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ScooterDTO> findScootersByName(@PathVariable("name") String name) {
        return rentalPlaceService.findScootersByName(name);
    }

    // methods below require authorities

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public RentalPlaceDTO createRentalPlace(@RequestBody CreateRentalPlaceDTO createData) {
        return rentalPlaceService.create(createData);
    }

    @PutMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public RentalPlaceDTO updateRentalPlaceByName(@PathVariable("name") String name, @RequestBody UpdateRentalPlaceDTO updateData) {
        return rentalPlaceService.updateByName(name, updateData);
    }

    @DeleteMapping(value = "/{name}")
    public boolean deleteRentalPlaceByName(@PathVariable("name") String name) {
        return rentalPlaceService.deleteByName(name);
    }
}
