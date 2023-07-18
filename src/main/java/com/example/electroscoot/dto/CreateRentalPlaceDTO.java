package com.example.electroscoot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateRentalPlaceDTO {
    private String name;
    private String city;
    private String street;
    private Integer house;
}
