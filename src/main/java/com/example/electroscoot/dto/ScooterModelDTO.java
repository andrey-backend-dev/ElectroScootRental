package com.example.electroscoot.dto;

import com.example.electroscoot.entities.ScooterModel;
import lombok.Data;

@Data
public class ScooterModelDTO {
    private String name;
    private float pricePerHour;
    private float startPrice;
    private int discount;

    public ScooterModelDTO(ScooterModel scooterModel) {
        this.name = scooterModel.getName();
        this.pricePerHour = scooterModel.getPricePerHour();
        this.startPrice = scooterModel.getStartPrice();
        this.discount = scooterModel.getDiscount();
    }
}
