package com.example.electroscoot.services;

import com.example.electroscoot.dao.RentalPlaceRepository;
import com.example.electroscoot.dao.ScooterRentalRepository;
import com.example.electroscoot.dao.ScooterRepository;
import com.example.electroscoot.dao.UserRepository;
import com.example.electroscoot.dto.CreateScooterRentalDTO;
import com.example.electroscoot.dto.ScooterRentalDTO;
import com.example.electroscoot.entities.RentalPlace;
import com.example.electroscoot.entities.Scooter;
import com.example.electroscoot.entities.ScooterModel;
import com.example.electroscoot.entities.ScooterRental;
import com.example.electroscoot.entities.User;
import com.example.electroscoot.services.interfaces.IScooterRentalService;
import com.example.electroscoot.utils.enums.StateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ScooterRentalService implements IScooterRentalService {

    @Autowired
    private ScooterRentalRepository scooterRentalRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ScooterRepository scooterRepository;
    @Autowired
    private RentalPlaceRepository rentalPlaceRepository;
    @Autowired
    private Clock clock;

    @Override
    public ScooterRentalDTO findById(int id) {
        return new ScooterRentalDTO(scooterRentalRepository.findById(id).orElse(null));
    }

    @Transactional
    @Override
    public ScooterRentalDTO create(CreateScooterRentalDTO createData) throws AccessDeniedException {
        Scooter scooter = scooterRepository.findById(createData.getScooterId()).orElse(null);

        if (scooter.getState() == StateEnum.BROKEN || scooter.getState() == StateEnum.RENTED )
            throw new AccessDeniedException("This scooter is not available for rent");

        User user = userRepository.findByUsername(createData.getUsername());
        ScooterModel scooterModel = scooter.getModel();

        if (user.getScooter() != null)
            throw new AccessDeniedException("Your previous rent has not been already closed");

        if (user.getSubscriptionTill() == null || user.getSubscriptionTill().isBefore(LocalDateTime.now(clock))) {
            float finalMoney = user.getMoney() - scooterModel.getStartPrice();
            if (finalMoney >= 0)
                user.setMoney(finalMoney);
            else
                throw new AccessDeniedException("User does not have enough money");
        }

        user.setScooter(scooter);

        ScooterRental scooterRental = new ScooterRental();
        scooterRental.setUser(user);
        scooterRental.setScooter(scooter);
        scooterRental.setScooterTakenAt(LocalDateTime.now(clock));
        scooterRental.setInitRentalPlace(scooter.getRentalPlace());

        scooter.setState(StateEnum.RENTED);
        scooter.setRentalPlace(null);

        return new ScooterRentalDTO(scooterRental);
    }

    @Transactional
    @Override
    public ScooterRentalDTO closeRentalById(int id, String rentalPlaceName) {
        ScooterRental scooterRental = scooterRentalRepository.findById(id).orElse(null);
        Scooter scooter = scooterRental.getScooter();
        ScooterModel scooterModel = scooter.getModel();
        User user = scooterRental.getUser();
        RentalPlace rentalPlace = rentalPlaceRepository.findByName(rentalPlaceName);

        double rentalHours = Math.ceil(ChronoUnit.SECONDS.between(
                LocalDateTime.now(clock), scooterRental.getScooterTakenAt()
        ) / 3600.0F);
        double cost = rentalHours * scooterModel.getPricePerHour() * (100 - scooterModel.getDiscount()) / 100;
        user.setMoney((float) (user.getMoney() - cost));

        scooter.setRentalPlace(rentalPlace);
        scooter.setState(StateEnum.OK);

        scooterRental.setFinalRentalPlace(rentalPlace);
        scooterRental.setScooterPassedAt(LocalDateTime.now(clock));

        return new ScooterRentalDTO(scooterRental);
    }

    @Override
    public List<ScooterRentalDTO> getList() {
        return ((List<ScooterRental>) scooterRentalRepository.findAll()).stream().map(ScooterRentalDTO::new).toList();
    }
}
