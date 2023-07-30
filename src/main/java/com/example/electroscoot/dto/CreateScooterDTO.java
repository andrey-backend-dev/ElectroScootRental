package com.example.electroscoot.dto;

import com.example.electroscoot.utils.enums.ScooterStateEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateScooterDTO {
    private String rentalPlaceName;
    @NotBlank(message = "Model is mandatory.")
    private String model;
    private ScooterStateEnum state;
}
