package com.example.electroscoot.utils.mappers;

import com.example.electroscoot.dto.ScooterRentalDTO;
import com.example.electroscoot.entities.Scooter;
import com.example.electroscoot.entities.ScooterModel;
import com.example.electroscoot.entities.ScooterRental;
import com.example.electroscoot.entities.User;
import com.example.electroscoot.utils.enums.ScooterStateEnum;
import org.mapstruct.Mapper;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ScooterRentalMapper {
    public ScooterRental toScooterRental(Scooter scooter, User user, ScooterModel model, Clock clock) {
        user.setScooter(scooter);

        ScooterRental scooterRental = new ScooterRental();
        scooterRental.setUser(user);
        scooterRental.setScooter(scooter);
        scooterRental.setScooterTakenAt(LocalDateTime.now(clock));
        scooterRental.setInitRentalPlace(scooter.getRentalPlace());
        scooterRental.setInitPricePerTime(model.getPricePerTime());
        scooterRental.setInitDiscount(model.getDiscount());

        scooter.setState(ScooterStateEnum.RENTED);
        scooter.setRentalPlace(null);
        return scooterRental;
    };
    public abstract ScooterRentalDTO scooterRentalToScooterRentalDto(ScooterRental scooterRental);
    public abstract List<ScooterRentalDTO> scooterRentalToScooterRentalDto(Iterable<ScooterRental> scooterRentals);
}
