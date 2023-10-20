package com.example.electroscoot.controllers;

import com.example.electroscoot.dto.CreateScooterRentalDTO;
import com.example.electroscoot.dto.RentalPlaceNameDTO;
import com.example.electroscoot.dto.ScooterRentalDTO;
import com.example.electroscoot.services.interfaces.IScooterRentalService;
import com.example.electroscoot.utils.enums.RentalStateEnum;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
}
