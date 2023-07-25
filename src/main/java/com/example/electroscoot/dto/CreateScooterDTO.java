package com.example.electroscoot.dto;

import com.example.electroscoot.utils.enums.ScooterStateEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateScooterDTO {
    private String rentalPlaceName;
    private String model;
    private ScooterStateEnum state;
}
