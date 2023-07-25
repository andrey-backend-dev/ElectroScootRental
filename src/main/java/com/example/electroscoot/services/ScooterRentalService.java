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
import com.example.electroscoot.infra.TriggerRentalSchedulerClock;
import com.example.electroscoot.services.interfaces.IScooterRentalService;
import com.example.electroscoot.utils.enums.RentalStateEnum;
import com.example.electroscoot.utils.enums.ScooterStateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.time.Clock;
import java.time.LocalDateTime;
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
    @Value("${business.pricePerTimeInSeconds}")
    private int pricePer;

    @Override
    public ScooterRentalDTO findById(int id) {
        return new ScooterRentalDTO(scooterRentalRepository.findById(id).orElse(null));
    }

    @Transactional
    @Override
    public ScooterRentalDTO create(CreateScooterRentalDTO createData) throws AccessDeniedException {
        Scooter scooter = scooterRepository.findById(createData.getScooterId()).orElse(null);

        if (scooter.getState() == ScooterStateEnum.BROKEN || scooter.getState() == ScooterStateEnum.RENTED )
            throw new AccessDeniedException("This scooter is not available for rent");

        User user = userRepository.findByUsername(createData.getUsername());
        ScooterModel scooterModel = scooter.getModel();

        if (user.getScooter() != null)
            throw new AccessDeniedException("Your previous rent has not been already closed");

        if (user.getSubscriptionTill() == null || user.getSubscriptionTill().isBefore(LocalDateTime.now(clock))) {
            float finalMoney = user.getMoney() - scooterModel.getStartPrice();
//            если у пользователя остались деньги хотя бы на 1 час аренды
            if (finalMoney >= scooterModel.getPricePerTime())
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
        scooterRental.setInitPricePerTime(scooterModel.getPricePerTime());
        scooterRental.setInitDiscount(scooterModel.getDiscount());

        scooter.setState(ScooterStateEnum.RENTED);
        scooter.setRentalPlace(null);

        scooterRental = scooterRentalRepository.save(scooterRental);

        triggerRentalSchedulerClock(scooterRental.getId());

        return new ScooterRentalDTO(scooterRental);
    }

    @Transactional
    @Override
    public RentalStateEnum takePaymentById(int id) {
        ScooterRental scooterRental = scooterRentalRepository.findById(id).orElse(null);
        User user = scooterRental.getUser();

        float cost = scooterRental.getInitPricePerTime() * (100 - scooterRental.getInitDiscount()) / 100;
        float userMoney = user.getMoney();
        if (scooterRental.getScooterPassedAt() == null && userMoney >= cost) {
            user.setMoney(userMoney - cost);
            return RentalStateEnum.OK;
        } else if (scooterRental.getScooterPassedAt() != null) {
            return RentalStateEnum.CLOSED;
        } else {
            try {
                closeRentalById(id, "");
            } catch (AccessDeniedException e) {
                e.printStackTrace();
            }

            return RentalStateEnum.BAD;
        }
    }

    @Transactional
    @Override
    public ScooterRentalDTO closeRentalById(int id, String rentalPlaceName) throws AccessDeniedException {
        ScooterRental scooterRental = scooterRentalRepository.findById(id).orElse(null);

        if (scooterRental.getScooterPassedAt() != null)
            throw new AccessDeniedException("this rental is already closed");

        Scooter scooter = scooterRental.getScooter();
        User user = scooterRental.getUser();

        RentalPlace rentalPlace = rentalPlaceRepository.findByName(rentalPlaceName);

        user.setScooter(null);

        scooter.setRentalPlace(rentalPlace);
        scooter.setState(ScooterStateEnum.OK);

        scooterRental.setFinalRentalPlace(rentalPlace);
        scooterRental.setScooterPassedAt(LocalDateTime.now(clock));

        return new ScooterRentalDTO(scooterRental);
    }

    @Override
    public List<ScooterRentalDTO> getList() {
        return ((List<ScooterRental>) scooterRentalRepository.findAll()).stream().map(ScooterRentalDTO::new).toList();
    }

    @Lookup
    public TriggerRentalSchedulerClock triggerRentalSchedulerClock(int scooterRentalId) {
        return new TriggerRentalSchedulerClock(scooterRentalId);
    }
}
