package com.example.electroscoot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateScooterModelDTO {
    private String name;
    private float pricePerHour;
    private float startPrice;
}
