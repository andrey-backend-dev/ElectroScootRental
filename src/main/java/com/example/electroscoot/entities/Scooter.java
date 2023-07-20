package com.example.electroscoot.entities;

import com.example.electroscoot.utils.enums.StateEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
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
    private StateEnum state = StateEnum.OK;
    @OneToMany(mappedBy = "scooter")
    private List<ScooterRental> scooterRentals = new ArrayList<>();
}
