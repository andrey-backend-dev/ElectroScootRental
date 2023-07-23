package com.example.electroscoot.services.interfaces;


import com.example.electroscoot.dto.CreateScooterRentalDTO;
import com.example.electroscoot.dto.ScooterRentalDTO;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface IScooterRentalService {

    ScooterRentalDTO findById(int id);

    ScooterRentalDTO create(CreateScooterRentalDTO createData) throws AccessDeniedException;

    ScooterRentalDTO closeRentalById(int id, String finalRentalPlaceName);

    List<ScooterRentalDTO> getList();
}
