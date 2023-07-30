package com.example.electroscoot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateScooterModelDTO {
    @NotBlank(message = "Name is mandatory.")
    private String name;
    @Positive(message = "Price per time must be more than zero.")
    private float pricePerTime;
    @PositiveOrZero(message = "Start price can not be less than zero.")
    private float startPrice;
}
