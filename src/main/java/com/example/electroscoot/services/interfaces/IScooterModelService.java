package com.example.electroscoot.services.interfaces;

import com.example.electroscoot.dto.CreateScooterModelDTO;
import com.example.electroscoot.dto.UpdateScooterModelDTO;
import com.example.electroscoot.entities.Role;
import com.example.electroscoot.entities.Scooter;
import com.example.electroscoot.entities.ScooterModel;

import java.util.List;

public interface IScooterModelService {
    ScooterModel findById(int id);
    ScooterModel findByName(String name);
    List<ScooterModel> getList();
    ScooterModel create(CreateScooterModelDTO createData);
    ScooterModel updateByName(String name, UpdateScooterModelDTO updateData);
    boolean deleteByName(String name);
    List<Scooter> getScootersByName(String name);
    boolean addScooterByName(String name, int scooterId);
    boolean removeScooterByName(String name, int scooterId);
}
