package com.example.electroscoot.services.interfaces;


import com.example.electroscoot.dto.CreateScooterRentalDTO;
import com.example.electroscoot.dto.RentalPlaceNameDTO;
import com.example.electroscoot.dto.ScooterRentalDTO;
import com.example.electroscoot.utils.enums.OrderEnum;
import com.example.electroscoot.utils.enums.RentalStateEnum;
import com.example.electroscoot.utils.enums.ScooterStateEnum;
import com.example.electroscoot.utils.enums.SortMethod;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface IScooterRentalService {

    ScooterRentalDTO findById(@Positive(message = "Id must be more than zero.") int id);

    ScooterRentalDTO create(@NotBlank(message = "Username is mandatory.") String username,
                            @Positive(message = "Id must be more than zero.") int scooterId);

    RentalStateEnum takePaymentById(@Positive(message = "Id must be more than zero.") int id);

    ScooterRentalDTO closeRentalById(@Positive(message = "Id must be more than zero.") int id,
                                     @NotBlank(message = "Rental place name is mandatory.") String rentalPlaceName);

    ScooterRentalDTO closeRentalByPrincipal(@NotBlank(message = "Username is mandatory.") String username,
                                            @NotBlank(message = "Rental place name is mandatory.") String rentalPlaceName);

    List<ScooterRentalDTO> findAll(Boolean passed);
}
