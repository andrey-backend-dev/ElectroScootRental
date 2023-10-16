package com.example.electroscoot.dto;

import com.example.electroscoot.entities.ScooterRental;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScooterRentalDTO {
    private int id;
    private String username;
    private int scooterId;
    private LocalDateTime scooterTakenAt;
    private LocalDateTime scooterPassedAt;
}
