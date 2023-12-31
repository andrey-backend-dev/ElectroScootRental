package com.example.electroscoot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class UpdateScooterModelDTO {
    private String name;
    private Float pricePerTime;
    private Float startPrice;
    private Integer discount;
}
