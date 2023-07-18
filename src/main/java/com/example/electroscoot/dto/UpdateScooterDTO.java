package com.example.electroscoot.dto;

import com.example.electroscoot.utils.enums.StateEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateScooterDTO {
    private int id;
    private String rentalPlaceName;
    private String model;
    private StateEnum state;
}
