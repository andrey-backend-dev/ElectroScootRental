package com.example.electroscoot.entities;

import com.example.electroscoot.utils.enums.ScooterStateEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Scooter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "rental_place_name", referencedColumnName = "name")
    private RentalPlace rentalPlace;
    @ManyToOne
    @JoinColumn(name = "model", referencedColumnName = "name")
    private ScooterModel model;
    @Enumerated(EnumType.STRING)
    private ScooterStateEnum state = ScooterStateEnum.OK;
    @OneToMany(mappedBy = "scooter")
    private List<ScooterRental> scooterRentals = new ArrayList<>();
    @OneToOne(mappedBy = "scooter")
    private User user;
}
