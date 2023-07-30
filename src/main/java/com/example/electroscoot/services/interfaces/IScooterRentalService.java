package com.example.electroscoot.services.interfaces;


import com.example.electroscoot.dto.CreateScooterRentalDTO;
import com.example.electroscoot.dto.RentalPlaceNameDTO;
import com.example.electroscoot.dto.ScooterRentalDTO;
import com.example.electroscoot.utils.enums.RentalStateEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface IScooterRentalService {

    ScooterRentalDTO findById(@Positive(message = "Id must be more than zero.") int id);

    ScooterRentalDTO create(@Valid CreateScooterRentalDTO createData);

    RentalStateEnum takePaymentById(@Positive(message = "Id must be more than zero.") int id);

    ScooterRentalDTO closeRentalById(@Positive(message = "Id must be more than zero.") int id,
                                     @Valid RentalPlaceNameDTO rentalPlaceNameDTO);

    List<ScooterRentalDTO> getList();
}
