package com.example.electroscoot.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "scooterrental")
public class ScooterRental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "scooter_id", referencedColumnName = "id")
    private Scooter scooter;
    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    private User user;
    private LocalDateTime scooterTakenAt;
    private LocalDateTime scooterPassedAt;
    @ManyToOne
    @JoinColumn(name = "init_rental_place_name", referencedColumnName = "name")
    private RentalPlace initRentalPlace;
    @ManyToOne
    @JoinColumn(name = "final_rental_place_name", referencedColumnName = "name")
    private RentalPlace finalRentalPlace;
    private float initPricePerTime;
    private int initDiscount;

}
