package com.example.electroscoot.dto;

import com.example.electroscoot.entities.ScooterModel;
import lombok.Data;

@Data
public class ScooterModelDTO {
    private String name;
    private float pricePerTime;
    private float startPrice;
    private int discount;
}
