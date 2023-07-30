package com.example.electroscoot.services.interfaces;

import com.example.electroscoot.dto.CreateRentalPlaceDTO;
import com.example.electroscoot.dto.RentalPlaceDTO;
import com.example.electroscoot.dto.ScooterDTO;
import com.example.electroscoot.dto.UpdateRentalPlaceDTO;
import com.example.electroscoot.utils.enums.SortMethod;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.util.List;

public interface IRentalPlaceService {
    RentalPlaceDTO findById(@Positive(message = "Id must be more than zero.") int id);
    RentalPlaceDTO findByName(@NotBlank(message = "Name is mandatory.") String name);
    List<RentalPlaceDTO> getList(@NotBlank(message = "Sort method is mandatory.") SortMethod sortMethod, String city);
    RentalPlaceDTO create(@Valid CreateRentalPlaceDTO createData);
    RentalPlaceDTO updateByName (@NotBlank(message = "Name is mandatory.") String name, UpdateRentalPlaceDTO updateData);
    boolean deleteByName(@NotBlank(message = "Name is mandatory.") String name);
    List<ScooterDTO> getScootersByName(@NotBlank(message = "Name is mandatory.") String name);
}
