package com.example.electroscoot.services.interfaces;

import com.example.electroscoot.dto.CreateScooterDTO;
import com.example.electroscoot.dto.ScooterDTO;
import com.example.electroscoot.dto.UpdateScooterDTO;

import java.util.List;

public interface IScooterService {
    ScooterDTO findById(int id);
    List<ScooterDTO> getList();
    ScooterDTO create(CreateScooterDTO createData);
    ScooterDTO updateById(UpdateScooterDTO updateData);
    boolean deleteById(int id);
}
