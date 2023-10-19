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
import com.example.electroscoot.infra.schedule.TriggerRentalSchedulerClock;
import com.example.electroscoot.services.interfaces.IScooterRentalService;
import com.example.electroscoot.utils.enums.RentalStateEnum;
import com.example.electroscoot.utils.enums.ScooterStateEnum;
import com.example.electroscoot.utils.mappers.ScooterRentalMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.security.Principal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ScooterRentalService implements IScooterRentalService {

    private final ScooterRentalRepository scooterRentalRepository;
    private final UserRepository userRepository;
    private final ScooterRepository scooterRepository;
    private final RentalPlaceRepository rentalPlaceRepository;
    private final Clock clock;
    private final Logger logger;
    private final ScooterRentalMapper mapper;
    @Value("${business.pricePerTimeInSeconds}")
    private int pricePerTimeInSeconds;

    @Lookup
    public TriggerRentalSchedulerClock triggerRentalSchedulerClock(int scooterRentalId) {
        return new TriggerRentalSchedulerClock(scooterRentalId);
    }

    @Override
    @Transactional(readOnly = true)
    public ScooterRentalDTO findById(@Positive(message = "Id must be more than zero.") int id) {
        return mapper.scooterRentalToScooterRentalDto(scooterRentalRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("The scooter rental with id " + id + " does not exist.");
        }));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScooterRentalDTO> findAll(Boolean passed) {
        if (passed == null)
            return mapper.scooterRentalToScooterRentalDto(scooterRentalRepository.findAll());
        else if (passed)
            return mapper.scooterRentalToScooterRentalDto(scooterRentalRepository.findByScooterPassedAtIsNotNull());
        else
            return mapper.scooterRentalToScooterRentalDto(scooterRentalRepository.findByScooterPassedAtIsNull());
    }

    @Transactional
    @Override
    public ScooterRentalDTO create(@NotBlank(message = "Username is mandatory.") String username,
                                   @Positive(message = "Id must be more than zero.") int scooterId) {
        Scooter scooter = scooterRepository.findById(scooterId).orElseThrow(() -> {
            return new IllegalArgumentException("The scooter with id " + scooterId + " does not exist.");
        });

        if (scooter.getState() == ScooterStateEnum.BROKEN || scooter.getState() == ScooterStateEnum.RENTED )
            throw new IllegalArgumentException("This scooter is not available for rent");

        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            return new IllegalArgumentException("The user with username " + username + " does not exist.");
        });
        ScooterModel scooterModel = scooter.getModel();

        if (user.getScooter() != null)
            throw new IllegalArgumentException("Your previous rent has not been already closed");

        ScooterRental scooterRental = mapper.toScooterRental(scooter, user, scooterModel, clock);

        scooterRental = scooterRentalRepository.save(scooterRental);

        makeFirstPayment(user, scooterModel, scooterRental);

        return mapper.scooterRentalToScooterRentalDto(scooterRental);
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
            closeRentalById(id, "null");
            return RentalStateEnum.BAD;
        }
    }

    @Transactional
    @Override
    public ScooterRentalDTO closeRentalById(@Positive(message = "Id must be more than zero.") int id,
                                            @NotBlank(message = "Rental place name is mandatory.") String rentalPlaceName) {
        ScooterRental scooterRental = scooterRentalRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("The scooter rental with id " + id + " does not exist.");
        });

        if (scooterRental.getScooterPassedAt() != null)
            throw new IllegalArgumentException("this rental is already closed");

        Scooter scooter = scooterRental.getScooter();
        User user = scooterRental.getUser();

        RentalPlace rentalPlace = null;
        if (rentalPlaceName != null && !rentalPlaceName.equals("null")) {
            rentalPlace = rentalPlaceRepository.findByName(rentalPlaceName).orElseThrow(() -> {
                return new IllegalArgumentException("The rental place with name " + rentalPlaceName + " does not exist.");
            });
        }

        user.setScooter(null);

        scooter.setRentalPlace(rentalPlace);
        scooter.setState(ScooterStateEnum.OK);

        scooterRental.setFinalRentalPlace(rentalPlace);
        scooterRental.setScooterPassedAt(LocalDateTime.now(clock));

        return mapper.scooterRentalToScooterRentalDto(scooterRental);
    }

    @Transactional
    @Override
    public ScooterRentalDTO closeRentalByPrincipal(@NotBlank(message = "Username is mandatory.") String username,
                                                   @NotBlank(message = "Rental place name is mandatory.") String rentalPlaceName) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            return new IllegalArgumentException("The user with username " + username + " does not exist.");
        });
        ScooterRental scooterRental = scooterRentalRepository.findByUserAndScooterPassedAtIsNull(user).orElseThrow(() -> {
            return new IllegalArgumentException("You do not have any opened rentals.");
        });
        return closeRentalById(scooterRental.getId(), rentalPlaceName);
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
