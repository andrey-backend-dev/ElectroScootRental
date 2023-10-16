package com.example.electroscoot.utils.mappers;

import com.example.electroscoot.dto.CreateScooterModelDTO;
import com.example.electroscoot.dto.ScooterModelDTO;
import com.example.electroscoot.entities.ScooterModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ScooterModelMapper {
    ScooterModel scooterModelDtoToScooterModel(CreateScooterModelDTO scooterModelDTO);
    ScooterModelDTO scooterModelToScooterModelDto(ScooterModel scooterModel);
    List<ScooterModelDTO> scooterModelToScooterModelDto(Iterable<ScooterModel> scooterModels);
}
