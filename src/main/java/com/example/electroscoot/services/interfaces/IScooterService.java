package com.example.electroscoot.services.interfaces;

import com.example.electroscoot.dto.CreateScooterDTO;
import com.example.electroscoot.dto.ScooterDTO;
import com.example.electroscoot.dto.UpdateScooterDTO;
import com.example.electroscoot.entities.RentalPlace;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import java.util.List;

public interface IScooterService {
    ScooterDTO findById(@Positive(message = "Id must be more than zero.") int id);
    List<ScooterDTO> getList();
    ScooterDTO create(@Valid CreateScooterDTO createData);
    ScooterDTO updateById(UpdateScooterDTO updateData);
    boolean deleteById(@Positive(message = "Id must be more than zero.") int id);
}
