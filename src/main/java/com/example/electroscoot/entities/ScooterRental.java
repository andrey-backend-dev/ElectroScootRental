package com.example.electroscoot.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class ScooterRental {
    @Id
    private int id;
    @ManyToOne
    private Scooter scooter;
    @ManyToOne
    private User user;
    private LocalDateTime scooterTakenAt;
    private LocalDateTime scooterPassedAt;
    @ManyToOne
    private RentalPlace initRentalPlace;
    @ManyToOne
    private RentalPlace finalRentalPlace;
    private float mileage;
}
