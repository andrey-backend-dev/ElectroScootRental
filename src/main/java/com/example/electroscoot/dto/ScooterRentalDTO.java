package com.example.electroscoot.dto;

import com.example.electroscoot.entities.ScooterRental;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScooterRentalDTO {
    private int id;
    private String username;
    private int scooterId;
    private String initRentalPlaceName;
    private String finalRentalPlaceName;
    private LocalDateTime scooterTakenAt;
    private LocalDateTime scooterPassedAt;

    public ScooterRentalDTO(ScooterRental scooterRental) {
        this.id = scooterRental.getId();
        this.username = scooterRental.getUser().getUsername();
        this.scooterId = scooterRental.getScooter().getId();
        this.initRentalPlaceName = scooterRental.getInitRentalPlace().getName();
        if (scooterRental.getFinalRentalPlace() != null)
            this.finalRentalPlaceName = scooterRental.getFinalRentalPlace().getName();
        this.scooterTakenAt = scooterRental.getScooterTakenAt();
        this.scooterPassedAt = scooterRental.getScooterPassedAt();
    }
}
