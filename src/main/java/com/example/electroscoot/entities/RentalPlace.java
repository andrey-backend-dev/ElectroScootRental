package com.example.electroscoot.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RentalPlace {
    @Id
    private int id;
    private String name;
    private String address;
    private int rating;
    @OneToMany(mappedBy = "rentalPlace")
    private List<Scooter> scooters = new ArrayList<>();
}
