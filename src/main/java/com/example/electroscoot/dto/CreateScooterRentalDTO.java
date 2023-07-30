package com.example.electroscoot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateScooterRentalDTO {
    @NotBlank(message = "Name is mandatory.")
    private String username;
    @Positive(message = "Id must be more than zero.")
    private int scooterId;
}
