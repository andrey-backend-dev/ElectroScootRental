package com.example.electroscoot.services.interfaces;

import com.example.electroscoot.dto.CreateRentalPlaceDTO;
import com.example.electroscoot.dto.RentalPlaceDTO;
import com.example.electroscoot.dto.ScooterDTO;
import com.example.electroscoot.dto.UpdateRentalPlaceDTO;
import com.example.electroscoot.utils.enums.SortMethod;

import java.util.List;

public interface IRentalPlaceService {
    RentalPlaceDTO findById(int id);
    RentalPlaceDTO findByName(String name);
    List<RentalPlaceDTO> getList(SortMethod sortMethod);
    List<RentalPlaceDTO> getListByCity(String city);
    RentalPlaceDTO create(CreateRentalPlaceDTO createData);
    RentalPlaceDTO updateByName (String name, UpdateRentalPlaceDTO updateData);
    boolean deleteByName(String name);
    List<ScooterDTO> getScootersByName(String name);
}
