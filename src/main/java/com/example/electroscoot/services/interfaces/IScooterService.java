package com.example.electroscoot.services.interfaces;

import com.example.electroscoot.dto.CreateScooterDTO;
import com.example.electroscoot.dto.ScooterDTO;
import com.example.electroscoot.dto.UpdateScooterDTO;
import com.example.electroscoot.entities.RentalPlace;
import com.example.electroscoot.utils.enums.OrderEnum;
import com.example.electroscoot.utils.enums.ScooterStateEnum;
import com.example.electroscoot.utils.enums.SortMethod;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public interface IScooterService {
    ScooterDTO findById(@Positive(message = "Id must be more than zero.") int id);
    List<ScooterDTO> findAll(@NotNull(message = "Sort method is mandatory.") SortMethod sortMethod,
                             @NotNull(message = "Ordering is mandatory.") OrderEnum ordering,
                             @NotNull(message = "Scooter-filter is mandatory.") ScooterStateEnum state);
    ScooterDTO create(@Valid CreateScooterDTO createData);
    ScooterDTO updateById(int id, UpdateScooterDTO updateData);
    boolean deleteById(@Positive(message = "Id must be more than zero.") int id);
}
