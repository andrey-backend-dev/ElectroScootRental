package com.example.electroscoot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateScooterModelDTO {
    private String name;
    private Float pricePerHour;
    private Float startPrice;
    private Integer discount;
}
