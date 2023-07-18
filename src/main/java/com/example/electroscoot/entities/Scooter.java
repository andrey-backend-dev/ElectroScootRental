package com.example.electroscoot.entities;

import com.example.electroscoot.utils.enums.StateEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
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
    private int id;
    @ManyToOne
    private RentalPlace rentalPlace;
    @ManyToOne
    private ScooterModel model;
//    @Enumerated(EnumType.STRING)
    private StateEnum state;
    @OneToMany(mappedBy = "scooter")
    private List<ScooterRental> scooterRentals = new ArrayList<>();
}
