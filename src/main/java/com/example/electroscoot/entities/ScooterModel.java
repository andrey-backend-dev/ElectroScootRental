package com.example.electroscoot.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class ScooterModel {
    @Id
    private int id;
    private String name;
    private float pricePerHour;
    private float startPrice;
    private int discount;
    @OneToMany(mappedBy = "model")
    private List<Scooter> scooters = new ArrayList<>();
}
