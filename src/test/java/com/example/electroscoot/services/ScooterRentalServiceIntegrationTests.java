package com.example.electroscoot.services;

import com.example.electroscoot.config.ScooterRentalServiceTestContextConfiguration;
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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.AccessDeniedException;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@RunWith(SpringRunner.class)
@Import(ScooterRentalServiceTestContextConfiguration.class)
public class ScooterRentalServiceIntegrationTests {

    @Autowired
    private IScooterRentalService scooterRentalService;
    @MockBean
    private ScooterRentalRepository scooterRentalRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private ScooterRepository scooterRepository;
    @MockBean
    private RentalPlaceRepository rentalPlaceRepository;
    @MockBean
    private Clock clock;

    private final Clock fixedClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

    @Before
    public void setUp() {
        int testScooterRentalId = 1;
        String testUsername = "testUsername";
        String testUsername2 = "testUsername2";
        String testUsername3 = "testUsername3";
        float testMoney = 10000;
        int testScooterId = 1;
        int testScooterId2 = 2;
        int testScooterId3 = 3;
        String testRentalPlaceName = "testRentalPlaceName";

        ScooterModel scooterModel = new ScooterModel();
        scooterModel.setName("testModelName");
        scooterModel.setStartPrice(300.0F);

        RentalPlace rentalPlace = new RentalPlace();
        rentalPlace.setName(testRentalPlaceName);

        Scooter scooter = new Scooter();
        scooter.setId(testScooterId);
        scooter.setModel(scooterModel);
        scooter.setRentalPlace(rentalPlace);

        Scooter scooter2 = new Scooter();
        scooter2.setId(testScooterId2);
        scooter2.setModel(scooterModel);
        scooter2.setRentalPlace(rentalPlace);

        Scooter scooter3 = new Scooter();
        scooter3.setId(testScooterId3);
        scooter3.setModel(scooterModel);
        scooter3.setRentalPlace(rentalPlace);

        User user = new User();
        user.setUsername(testUsername);
        user.setMoney(testMoney);

        User user2 = new User();
        user2.setUsername(testUsername2);
        user2.setMoney(0);

        User user3 = new User();
        user3.setUsername(testUsername3);
        user3.setMoney(10000);
        user3.setScooter(new Scooter());

        ScooterRental scooterRental = new ScooterRental();
        scooterRental.setId(testScooterId);
        scooterRental.setScooter(scooter);
        scooterRental.setInitRentalPlace(rentalPlace);
        scooterRental.setUser(user);
        scooterRental.setScooterTakenAt(LocalDateTime.now(fixedClock));

        Mockito.when(clock.instant()).thenReturn(fixedClock.instant());
        Mockito.when(clock.getZone()).thenReturn(fixedClock.getZone());
        Mockito.when(scooterRepository.findById(testScooterId)).thenReturn(Optional.of(scooter));
        Mockito.when(scooterRepository.findById(testScooterId2)).thenReturn(Optional.of(scooter2));
        Mockito.when(scooterRepository.findById(testScooterId3)).thenReturn(Optional.of(scooter3));
        Mockito.when(rentalPlaceRepository.findByName(testRentalPlaceName)).thenReturn(rentalPlace);
        Mockito.when(userRepository.findByUsername(testUsername)).thenReturn(user);
        Mockito.when(userRepository.findByUsername(testUsername2)).thenReturn(user2);
        Mockito.when(userRepository.findByUsername(testUsername3)).thenReturn(user3);

        Mockito.when(scooterRentalRepository.findById(testScooterRentalId)).thenReturn(Optional.of(scooterRental));
    }

    @Test
    public void createTest() {
        String testUsername = "testUsername";
        String testUsername2 = "testUsername2";
        String testUsername3 = "testUsername3";
        int testScooterId = 1;
        int testScooterId2 = 2;
        int testScooterId3 = 3;
        String testRentalPlaceName = "testRentalPlaceName";
        float testMoney = 10000;

        ScooterModel scooterModel = new ScooterModel();
        scooterModel.setName("testModelName");
        scooterModel.setStartPrice(300.0F);

        RentalPlace rentalPlace = new RentalPlace();
        rentalPlace.setName(testRentalPlaceName);

        Scooter scooter = new Scooter();
        scooter.setId(testScooterId);
        scooter.setModel(scooterModel);
        scooter.setState(StateEnum.RENTED);

        User user = new User();
        user.setUsername(testUsername);
        user.setMoney(testMoney);

        ScooterRental scooterRental = new ScooterRental();
        scooterRental.setScooter(scooter);
        scooterRental.setInitRentalPlace(rentalPlace);
        scooterRental.setUser(user);
        scooterRental.setScooterTakenAt(LocalDateTime.now(fixedClock));

        CreateScooterRentalDTO createData = new CreateScooterRentalDTO(testUsername, testScooterId);

        ScooterRentalDTO expectedScooterRentalDTO = new ScooterRentalDTO(scooterRental);

        ScooterRentalDTO resultScooterRentalDTO = null;

        try {
            resultScooterRentalDTO = scooterRentalService.create(createData);
        } catch (AccessDeniedException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(expectedScooterRentalDTO, resultScooterRentalDTO);

//        scooter status is rented
        Assert.assertThrows(AccessDeniedException.class, () -> {
            scooterRentalService.create(createData);
        });

//        not enough money
        Assert.assertThrows(AccessDeniedException.class, () -> {
            createData.setUsername(testUsername2);
            createData.setScooterId(testScooterId2);
            scooterRentalService.create(createData);
        });

//        user already rented scooter
        Assert.assertThrows(AccessDeniedException.class, () -> {
            createData.setUsername(testUsername3);
            createData.setScooterId(testScooterId3);
            scooterRentalService.create(createData);
        });
    }

    @Test
    public void closeRentalByIdTest() {
        int testScooterRentalId = 1;
        String testUsername = "testUsername";
        int testScooterId = 1;
        String testRentalPlaceName = "testRentalPlaceName";

        Scooter scooter = new Scooter();
        scooter.setId(testScooterId);
        RentalPlace rentalPlace = new RentalPlace();
        rentalPlace.setName(testRentalPlaceName);
        User user = new User();
        user.setUsername(testUsername);

        ScooterRental scooterRental = new ScooterRental();
        scooterRental.setId(testScooterRentalId);
        scooterRental.setScooter(scooter);
        scooterRental.setInitRentalPlace(rentalPlace);
        scooterRental.setUser(user);
        scooterRental.setScooterTakenAt(LocalDateTime.now(fixedClock));
        scooterRental.setScooterPassedAt(LocalDateTime.now(fixedClock));
        scooterRental.setFinalRentalPlace(rentalPlace);

        ScooterRentalDTO expectedScooterRentalDTO = new ScooterRentalDTO(scooterRental);

        ScooterRentalDTO resultScooterRentalDTO = scooterRentalService.closeRentalById(testScooterRentalId, testRentalPlaceName);

        Assert.assertEquals(expectedScooterRentalDTO, resultScooterRentalDTO);
    }

}
