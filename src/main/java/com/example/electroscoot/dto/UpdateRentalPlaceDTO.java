package com.example.electroscoot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class UpdateRentalPlaceDTO {
    private String name;
    private Integer rating;
    private String city;
    private String street;
    private Integer house;
}
