package com.example.electroscoot.utils.mappers;

import com.example.electroscoot.dto.CreateRentalPlaceDTO;
import com.example.electroscoot.dto.RentalPlaceDTO;
import com.example.electroscoot.entities.RentalPlace;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RentalPlaceMapper {

    RentalPlace createRentalPlaceDtoToRentalPlace(CreateRentalPlaceDTO rentalPlaceDTO);
    RentalPlaceDTO rentalPlaceToRentalPlaceDto(RentalPlace rentalPlace);
    List<RentalPlaceDTO> rentalPlaceToRentalPlaceDto(Iterable<RentalPlace> rentalPlace);
}
