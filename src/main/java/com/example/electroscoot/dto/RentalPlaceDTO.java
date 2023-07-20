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
        this.address = rentalPlace.getAddress();
        this.rating = rentalPlace.getRating();
    }
}
