package com.example.electroscoot.controllers;

import com.example.electroscoot.dto.ScooterRentalDTO;
import com.example.electroscoot.services.interfaces.IScooterRentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/rentals")
public class ScooterRentalController {
    private final IScooterRentalService scooterRentalService;

    @PostMapping(value = "/rent", produces = MediaType.APPLICATION_JSON_VALUE)
    public ScooterRentalDTO create(Principal principal, @RequestParam("scooter") int scooterId) {
        return scooterRentalService.create(principal.getName(), scooterId);
    }

    @PatchMapping(value = "/rent/close", produces = MediaType.APPLICATION_JSON_VALUE)
    public ScooterRentalDTO closeRentalById(Principal principal, @RequestParam("rental-place") String rentalPlace) {
        return scooterRentalService.closeRentalByPrincipal(principal.getName(), rentalPlace);
    }

    // methods below require authorities

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ScooterRentalDTO> findAllScooterRentals(@RequestParam(value = "passed-filter", required = false) Boolean passed) {
        return scooterRentalService.findAll(passed);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ScooterRentalDTO findScooterRentalById(@PathVariable("id") int id) {
        return scooterRentalService.findById(id);
    }

    @PatchMapping(value = "/{id}/close", produces = MediaType.APPLICATION_JSON_VALUE)
    public ScooterRentalDTO closeRentalById(@PathVariable("id") int id, @RequestParam("rental-place") String rentalPlace) {
        return scooterRentalService.closeRentalById(id, rentalPlace);
    }
}
