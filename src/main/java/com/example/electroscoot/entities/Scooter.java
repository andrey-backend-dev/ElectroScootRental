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
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
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
    @NotNull
    private ScooterModel model;
    @Enumerated(EnumType.STRING)
    private ScooterStateEnum state = ScooterStateEnum.OK;
    @OneToMany(mappedBy = "scooter")
    private Set<ScooterRental> scooterRentals;
    @OneToOne(mappedBy = "scooter")
    private User user;
}
