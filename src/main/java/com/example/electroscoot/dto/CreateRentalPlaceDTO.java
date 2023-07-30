package com.example.electroscoot.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateRentalPlaceDTO {
    @NotBlank(message = "Name is mandatory.")
    private String name;
    @NotBlank(message = "City is mandatory.")
    private String city;
    @NotBlank(message = "Street is mandatory.")
    private String street;
    private Integer house;
}
