package com.example.electroscoot.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Getter
@Setter
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
    @NotNull(message = "User is mandatory.")
    private User user;
    @PastOrPresent(message = "Scooter taken at future. Validation issues.")
    private LocalDateTime scooterTakenAt;
    private LocalDateTime scooterPassedAt;
    @ManyToOne
    @JoinColumn(name = "init_rental_place_name", referencedColumnName = "name")
    private RentalPlace initRentalPlace;
    @ManyToOne
    @JoinColumn(name = "final_rental_place_name", referencedColumnName = "name")
    private RentalPlace finalRentalPlace;
    @Positive(message = "Initial Price Per Time must be positive.")
    private float initPricePerTime;
    @PositiveOrZero(message = "Initial Discount can not be less than zero.")
    private int initDiscount;
}
