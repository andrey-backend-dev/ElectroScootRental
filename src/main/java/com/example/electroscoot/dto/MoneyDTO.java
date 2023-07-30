package com.example.electroscoot.dto;

import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MoneyDTO {
    @Positive(message = "Money must be more than zero.")
    private float money;
}
