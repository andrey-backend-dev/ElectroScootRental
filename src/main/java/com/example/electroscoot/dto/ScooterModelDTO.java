package com.example.electroscoot.dto;

import com.example.electroscoot.entities.ScooterModel;
import lombok.Data;

@Data
public class ScooterModelDTO {
    private String name;
    private float pricePerTime;
    private float startPrice;
    private int discount;

    public ScooterModelDTO(ScooterModel scooterModel) {
        this.name = scooterModel.getName();
        this.pricePerTime = scooterModel.getPricePerTime();
        this.startPrice = scooterModel.getStartPrice();
        this.discount = scooterModel.getDiscount();
    }
}
