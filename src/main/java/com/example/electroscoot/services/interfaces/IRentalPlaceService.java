package com.example.electroscoot.services.interfaces;

import com.example.electroscoot.dto.CreateRentalPlaceDTO;
import com.example.electroscoot.dto.RentalPlaceDTO;
import com.example.electroscoot.dto.ScooterDTO;
import com.example.electroscoot.dto.UpdateRentalPlaceDTO;

import java.util.List;

public interface IRentalPlaceService {
    RentalPlaceDTO findById(int id);
    RentalPlaceDTO findByName(String name);
    List<RentalPlaceDTO> getList();
    RentalPlaceDTO create(CreateRentalPlaceDTO createData);
    RentalPlaceDTO updateByName (String name, UpdateRentalPlaceDTO updateData);
    boolean deleteByName(String name);
    List<RentalPlaceDTO> getListSortedByAddresses();
    List<ScooterDTO> getScootersByName(String name);
}
