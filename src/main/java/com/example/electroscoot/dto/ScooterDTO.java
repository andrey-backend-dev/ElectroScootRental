package com.example.electroscoot.dto;

import com.example.electroscoot.entities.Scooter;
import com.example.electroscoot.utils.enums.ScooterStateEnum;
import lombok.Data;

@Data
public class ScooterDTO {
    private int id;
    private String rentalPlaceName;
    private String model;
    private ScooterStateEnum state;

    public ScooterDTO(Scooter scooter) {
        this.id = scooter.getId();
        this.rentalPlaceName = scooter.getRentalPlace().getName();
        this.model = scooter.getModel().getName();
        this.state = scooter.getState();
    }
}
