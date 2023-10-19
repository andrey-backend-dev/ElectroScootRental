package com.example.electroscoot.services.interfaces;

import com.example.electroscoot.dto.CreateScooterModelDTO;
import com.example.electroscoot.dto.ScooterDTO;
import com.example.electroscoot.dto.ScooterModelDTO;
import com.example.electroscoot.dto.UpdateScooterModelDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.util.List;

public interface IScooterModelService {
    ScooterModelDTO findById(@Positive(message = "Id must be more than zero.") int id);
    ScooterModelDTO findByName(@NotBlank(message = "Name is mandatory.") String name);
    List<ScooterModelDTO> findAll();
    ScooterModelDTO create(@Valid CreateScooterModelDTO createData);
    ScooterModelDTO updateByName(@NotBlank(message = "Name is mandatory.") String name, UpdateScooterModelDTO updateData);
    boolean deleteByName(@NotBlank(message = "Name is mandatory.") String name);
    List<ScooterDTO> findScootersByName(@NotBlank(message = "Name is mandatory.") String name);
}
