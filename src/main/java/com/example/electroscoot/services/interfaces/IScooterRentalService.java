package com.example.electroscoot.services.interfaces;


import com.example.electroscoot.dto.CreateScooterRentalDTO;
import com.example.electroscoot.dto.ScooterRentalDTO;
import com.example.electroscoot.utils.enums.RentalStateEnum;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface IScooterRentalService {

    ScooterRentalDTO findById(int id);

    ScooterRentalDTO create(CreateScooterRentalDTO createData) throws AccessDeniedException;

    RentalStateEnum takePaymentById(int id);

    ScooterRentalDTO closeRentalById(int id, String finalRentalPlaceName) throws AccessDeniedException;

    List<ScooterRentalDTO> getList();
}
