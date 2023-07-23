package com.example.electroscoot.dto;

import com.example.electroscoot.entities.RentalPlace;
import lombok.Data;

@Data
public class RentalPlaceDTO {
    private String name;
    private String address;
    private int rating;

    public RentalPlaceDTO(RentalPlace rentalPlace) {
        this.name = rentalPlace.getName();
        if (rentalPlace.getHouse() != null)
            this.address = String.join(",", rentalPlace.getCity(), rentalPlace.getStreet(), Integer.toString(rentalPlace.getHouse()));
        else
            this.address = String.join(",", rentalPlace.getCity(), rentalPlace.getStreet());
        this.rating = rentalPlace.getRating();
    }
}
