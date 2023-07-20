package com.example.electroscoot.services.interfaces;

import com.example.electroscoot.dto.CreateScooterModelDTO;
import com.example.electroscoot.dto.ScooterDTO;
import com.example.electroscoot.dto.ScooterModelDTO;
import com.example.electroscoot.dto.UpdateScooterModelDTO;

import java.util.List;

public interface IScooterModelService {
    ScooterModelDTO findById(int id);
    ScooterModelDTO findByName(String name);
    List<ScooterModelDTO> getList();
    ScooterModelDTO create(CreateScooterModelDTO createData);
    ScooterModelDTO updateByName(String name, UpdateScooterModelDTO updateData);
    boolean deleteByName(String name);
    List<ScooterDTO> getScootersByName(String name);
}
