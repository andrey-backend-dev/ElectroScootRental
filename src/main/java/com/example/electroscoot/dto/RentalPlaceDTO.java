package com.example.electroscoot.dto;

import com.example.electroscoot.entities.RentalPlace;
import lombok.Data;

@Data
public class RentalPlaceDTO {
    private String name;
    private String city;
    private String street;
    private int house;
    private int rating;
}
