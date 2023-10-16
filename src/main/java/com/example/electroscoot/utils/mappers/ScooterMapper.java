package com.example.electroscoot.utils.mappers;

import com.example.electroscoot.dto.CreateScooterDTO;
import com.example.electroscoot.dto.ScooterDTO;
import com.example.electroscoot.entities.Scooter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ScooterMapper {
    @Mapping(target = "model", expression = "java(scooter.getModel().getName())")
    ScooterDTO scooterToScooterDto(Scooter scooter);
    List<ScooterDTO> scooterToScooterDto(Iterable<Scooter> scooters);
}
