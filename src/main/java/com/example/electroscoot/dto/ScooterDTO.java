package com.example.electroscoot.dto;

import com.example.electroscoot.entities.Scooter;
import com.example.electroscoot.utils.enums.ScooterStateEnum;
import lombok.Data;

@Data
public class ScooterDTO {
    private int id;
    private String model;
    private ScooterStateEnum state;
}
