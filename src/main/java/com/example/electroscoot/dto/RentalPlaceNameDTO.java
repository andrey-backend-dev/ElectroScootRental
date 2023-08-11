package com.example.electroscoot.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalPlaceNameDTO {
    @NotBlank(message = "Rental place name is mandatory.")
    private String name;
}
