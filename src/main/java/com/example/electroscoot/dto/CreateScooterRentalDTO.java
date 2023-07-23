package com.example.electroscoot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateScooterRentalDTO {
    private String username;
    private int scooterId;
}
