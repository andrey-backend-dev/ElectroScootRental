package com.example.electroscoot.services.interfaces;

import com.example.electroscoot.dto.CreateRentalPlaceDTO;
import com.example.electroscoot.dto.UpdateRentalPlaceDTO;
import com.example.electroscoot.entities.RentalPlace;
import com.example.electroscoot.entities.Scooter;

import java.util.List;

public interface IRentalPlaceService {
    RentalPlace findById(int id);
    RentalPlace findByName(String name);
    List<RentalPlace> getList();
    RentalPlace create(CreateRentalPlaceDTO createData);
    RentalPlace updateByName (String name, UpdateRentalPlaceDTO updateData);
    boolean deleteByName(String name);
    List<RentalPlace> getListSortedByAddresses();
    List<Scooter> getScootersByName(String name);
    boolean addScooterByName(String name, int scooterId);
    boolean removeScooterByName(String name, int scooterId);
}
