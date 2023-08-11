package com.example.electroscoot.services;

import com.example.electroscoot.dao.RentalPlaceRepository;
import com.example.electroscoot.dao.ScooterRentalRepository;
import com.example.electroscoot.dao.ScooterRepository;
import com.example.electroscoot.dao.UserRepository;
import com.example.electroscoot.dto.CreateScooterRentalDTO;
import com.example.electroscoot.dto.RentalPlaceNameDTO;
import com.example.electroscoot.dto.ScooterRentalDTO;
import com.example.electroscoot.entities.RentalPlace;
import com.example.electroscoot.entities.Scooter;
import com.example.electroscoot.entities.ScooterModel;
import com.example.electroscoot.entities.ScooterRental;
import com.example.electroscoot.entities.User;
import com.example.electroscoot.exceptions.custom.CustomConflictException;
import com.example.electroscoot.infra.schedule.TriggerRentalSchedulerClock;
import com.example.electroscoot.services.interfaces.IScooterRentalService;
import com.example.electroscoot.utils.enums.RentalStateEnum;
import com.example.electroscoot.utils.enums.ScooterStateEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Validated
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
    @Autowired
    private Logger logger;
    @Value("${business.pricePerTimeInSeconds}")
    private int pricePerTimeInSeconds;

    @Lookup
    public TriggerRentalSchedulerClock triggerRentalSchedulerClock(int scooterRentalId) {
        return new TriggerRentalSchedulerClock(scooterRentalId);
    }

    @Override
    @Transactional(readOnly = true)
    public ScooterRentalDTO findById(@Positive(message = "Id must be more than zero.") int id) {
        return new ScooterRentalDTO(scooterRentalRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("The scooter rental with id " + id + " does not exist.");
        }));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScooterRentalDTO> getList(Boolean passed) {
        if (passed == null)
            return ((List<ScooterRental>) scooterRentalRepository.findAll()).stream().map(ScooterRentalDTO::new).toList();
        else if (passed)
            return scooterRentalRepository.findByScooterPassedAtIsNotNull().stream().map(ScooterRentalDTO::new).toList();
        else
            return scooterRentalRepository.findByScooterPassedAtIsNull().stream().map(ScooterRentalDTO::new).toList();
    }

    @Transactional
    @Override
    public ScooterRentalDTO create(@Valid CreateScooterRentalDTO createData) {
        Scooter scooter = scooterRepository.findById(createData.getScooterId()).orElseThrow(() -> {
            return new IllegalArgumentException("The scooter with id " + createData.getScooterId() + " does not exist.");
        });

        if (scooter.getState() == ScooterStateEnum.BROKEN || scooter.getState() == ScooterStateEnum.RENTED )
            throw new CustomConflictException("This scooter is not available for rent");

        User user = userRepository.findByUsername(createData.getUsername()).orElseThrow(() -> {
            return new IllegalArgumentException("The user with username " + createData.getUsername() + " does not exist.");
        });
        ScooterModel scooterModel = scooter.getModel();

        if (user.getScooter() != null)
            throw new CustomConflictException("Your previous rent has not been already closed");

        ScooterRental scooterRental = setUpEntities(scooter, user, scooterModel);

        scooterRental = scooterRentalRepository.save(scooterRental);

        makeFirstPayment(user, scooterModel, scooterRental);

        return new ScooterRentalDTO(scooterRental);
    }

    @Transactional
    @Override
    public RentalStateEnum takePaymentById(@Positive(message = "Id must be more than zero.") int id) {
        ScooterRental scooterRental = scooterRentalRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("The scooter rental with id " + id + " does not exist.");
        });
        User user = scooterRental.getUser();

        float cost = scooterRental.getInitPricePerTime() * (100 - scooterRental.getInitDiscount()) / 100;
        float userMoney = user.getMoney();
        if (scooterRental.getScooterPassedAt() == null && userMoney >= cost) {
            user.setMoney(userMoney - cost);
            logger.info("Payment for scooter rental with id " + id + " accepted. Renewal rental for " + pricePerTimeInSeconds + " seconds.");
            return RentalStateEnum.OK;
        } else if (scooterRental.getScooterPassedAt() != null) {
            logger.info("Payment for scooter rental with id " + id + " is not accepted. Rental is closed.");
            return RentalStateEnum.CLOSED;
        } else {
            logger.info("Payment for scooter rental with id " + id + " is not accepted. " +
                    "User with username " + user.getUsername() + " does not have enough money. Rental will be closed.");
            closeRentalById(id, new RentalPlaceNameDTO("null"));
            return RentalStateEnum.BAD;
        }
    }

    @Transactional
    @Override
    public ScooterRentalDTO closeRentalById(@Positive(message = "Id must be more than zero.") int id,
                                            @Valid RentalPlaceNameDTO rentalPlaceNameDTO) {
        ScooterRental scooterRental = scooterRentalRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("The scooter rental with id " + id + " does not exist.");
        });

        if (scooterRental.getScooterPassedAt() != null)
            throw new CustomConflictException("this rental is already closed");

        Scooter scooter = scooterRental.getScooter();
        User user = scooterRental.getUser();

        RentalPlace rentalPlace = null;
        if (rentalPlaceNameDTO.getName() != null && !rentalPlaceNameDTO.getName().equals("null")) {
            rentalPlace = rentalPlaceRepository.findByName(rentalPlaceNameDTO.getName()).orElseThrow(() -> {
                return new IllegalArgumentException("The rental place with name " + rentalPlaceNameDTO.getName() + " does not exist.");
            });
        }

        user.setScooter(null);

        scooter.setRentalPlace(rentalPlace);
        scooter.setState(ScooterStateEnum.OK);

        scooterRental.setFinalRentalPlace(rentalPlace);
        scooterRental.setScooterPassedAt(LocalDateTime.now(clock));

        return new ScooterRentalDTO(scooterRental);
    }

    private ScooterRental setUpEntities(Scooter scooter, User user, ScooterModel scooterModel) {
        user.setScooter(scooter);

        ScooterRental scooterRental = new ScooterRental();
        scooterRental.setUser(user);
        scooterRental.setScooter(scooter);
        scooterRental.setScooterTakenAt(LocalDateTime.now(clock));
        scooterRental.setInitRentalPlace(scooter.getRentalPlace());
        scooterRental.setInitPricePerTime(scooterModel.getPricePerTime());
        scooterRental.setInitDiscount(scooterModel.getDiscount());

        scooter.setState(ScooterStateEnum.RENTED);
        scooter.setRentalPlace(null);
        return scooterRental;
    }

    private void makeFirstPayment(User user, ScooterModel scooterModel, ScooterRental scooterRental) {
        float startPrice = user.getSubscriptionTill() == null || user.getSubscriptionTill().isBefore(LocalDateTime.now(clock))
                ? scooterModel.getStartPrice()
                : 0;

        // сразу снимаем деньги за взятие аренды + за час аренды, на таймере стоит initdelay размером в промежуток
        // между выплатами, так что все ок
        float finalMoney = user.getMoney() - startPrice - scooterModel.getPricePerTime() * (100 - scooterModel.getDiscount()) / 100;
        if (finalMoney >= 0) {
            user.setMoney(finalMoney);
            logger.info("Payment for scooter rental with id " + scooterRental.getId() + " accepted. Starting rental for " + pricePerTimeInSeconds + " seconds.");
        } else {
            throw new IllegalArgumentException("User does not have enough money");
        }

        triggerRentalSchedulerClock(scooterRental.getId());
    }
}
