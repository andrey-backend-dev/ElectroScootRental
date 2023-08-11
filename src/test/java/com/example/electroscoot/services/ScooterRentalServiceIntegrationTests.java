package com.example.electroscoot.services;

import com.example.electroscoot.configs.ScooterRentalServiceTestContextConfiguration;
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
import com.example.electroscoot.services.interfaces.IScooterRentalService;
import com.example.electroscoot.utils.enums.RentalStateEnum;
import com.example.electroscoot.utils.enums.ScooterStateEnum;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.AccessDeniedException;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@RunWith(SpringRunner.class)
@Import(ScooterRentalServiceTestContextConfiguration.class)
@PropertySource("classpath:application.properties")
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
    @Value("${business.pricePerTimeInSeconds}")
    private int pricePerTime;

    private final Clock fixedClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

    @Before
    public void setUp() {
        int testScooterRentalId = 1;
        int testScooterRentalId2 = 2;
        int testScooterRentalId3 = 3;
        String testUsername = "testUsername";
        String testUsername2 = "testUsername2";
        String testUsername3 = "testUsername3";
        float testMoney = 100000;
        int testScooterId = 1;
        int testScooterId2 = 2;
        int testScooterId3 = 3;
        int testHoursScooterRental = 1;
        float testStartPrice = 300.0F;
        float testPricePerHour = 100.0F;
        int testDiscount = 10;
        String testRentalPlaceName = "testRentalPlaceName";

        ScooterModel scooterModel = new ScooterModel();
        scooterModel.setName("testModelName");
        scooterModel.setStartPrice(testStartPrice);
        scooterModel.setPricePerTime(testPricePerHour);
        scooterModel.setDiscount(testDiscount);

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

        Scooter scooter4rental = new Scooter();
        scooter4rental.setId(testScooterId);
        scooter4rental.setRentalPlace(null);
        scooter4rental.setModel(scooterModel);
        scooter4rental.setState(ScooterStateEnum.RENTED);

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

        User user4rental = new User();
        user4rental.setUsername(testUsername);
        user4rental.setMoney(testMoney - testStartPrice);
        user4rental.setScooter(scooter4rental);

        ScooterRental scooterRental = new ScooterRental();
        scooterRental.setScooter(scooter4rental);
        scooterRental.setInitRentalPlace(rentalPlace);
        scooterRental.setUser(user4rental);
        scooterRental.setScooterTakenAt(LocalDateTime.now(fixedClock));
        scooterRental.setInitPricePerTime(scooterModel.getPricePerTime());
        scooterRental.setInitDiscount(scooterModel.getDiscount());

        ScooterRental scooterRental1 = new ScooterRental();
        scooterRental1.setId(testScooterRentalId);
        scooterRental1.setScooter(scooter4rental);
        scooterRental1.setInitRentalPlace(rentalPlace);
        scooterRental1.setUser(user4rental);
        scooterRental1.setScooterTakenAt(LocalDateTime.now(fixedClock).minusHours(testHoursScooterRental));
        scooterRental1.setInitPricePerTime(scooterModel.getPricePerTime());
        scooterRental1.setInitDiscount(scooterModel.getDiscount());

        ScooterRental scooterRental2 = new ScooterRental();
        scooterRental2.setId(testScooterRentalId2);
        scooterRental2.setScooter(scooter4rental);
        scooterRental2.setInitRentalPlace(rentalPlace);
        scooterRental2.setUser(user2);
        scooterRental2.setScooterTakenAt(LocalDateTime.now(fixedClock));
        scooterRental2.setInitPricePerTime(scooterModel.getPricePerTime());
        scooterRental2.setInitDiscount(scooterModel.getDiscount());

        ScooterRental scooterRental3 = new ScooterRental();
        scooterRental3.setId(testScooterRentalId3);
        scooterRental3.setScooter(scooter4rental);
        scooterRental3.setInitRentalPlace(rentalPlace);
        scooterRental3.setUser(user4rental);
        scooterRental3.setScooterTakenAt(LocalDateTime.now(fixedClock));
        scooterRental3.setInitPricePerTime(scooterModel.getPricePerTime());
        scooterRental3.setInitDiscount(scooterModel.getDiscount());
        scooterRental3.setScooterPassedAt(LocalDateTime.now(fixedClock));


        Mockito.when(clock.instant()).thenReturn(fixedClock.instant());
        Mockito.when(clock.getZone()).thenReturn(fixedClock.getZone());
        Mockito.when(scooterRepository.findById(testScooterId)).thenReturn(Optional.of(scooter));
        Mockito.when(scooterRepository.findById(testScooterId2)).thenReturn(Optional.of(scooter2));
        Mockito.when(scooterRepository.findById(testScooterId3)).thenReturn(Optional.of(scooter3));
        Mockito.when(rentalPlaceRepository.findByName(testRentalPlaceName)).thenReturn(Optional.of(rentalPlace));
        Mockito.when(userRepository.findByUsername(testUsername)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.findByUsername(testUsername2)).thenReturn(Optional.of(user2));
        Mockito.when(userRepository.findByUsername(testUsername3)).thenReturn(Optional.of(user3));
        Mockito.when(scooterRentalRepository.save(scooterRental)).thenReturn(scooterRental);

        Mockito.when(scooterRentalRepository.findById(testScooterRentalId)).thenReturn(Optional.of(scooterRental1));
        Mockito.when(scooterRentalRepository.findById(testScooterRentalId2)).thenReturn(Optional.of(scooterRental2));
        Mockito.when(scooterRentalRepository.findById(testScooterRentalId3)).thenReturn(Optional.of(scooterRental3));
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
        scooter.setState(ScooterStateEnum.RENTED);

        User user = new User();
        user.setUsername(testUsername);
        user.setMoney(testMoney);

        ScooterRental scooterRental = new ScooterRental();
        scooterRental.setScooter(scooter);
        scooterRental.setInitRentalPlace(rentalPlace);
        scooterRental.setUser(user);
        scooterRental.setScooterTakenAt(LocalDateTime.now(fixedClock));
        scooterRental.setInitPricePerTime(scooterModel.getPricePerTime());
        scooterRental.setInitDiscount(scooterModel.getDiscount());

        CreateScooterRentalDTO createData = new CreateScooterRentalDTO(testUsername, testScooterId);

        ScooterRentalDTO expectedScooterRentalDTO = new ScooterRentalDTO(scooterRental);

        ScooterRentalDTO resultScooterRentalDTO = scooterRentalService.create(createData);

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
    public void takePaymentByIdTest() {
        int testScooterRentalId = 1;
        int testScooterRentalId2 = 2;
        int testScooterRentalId3 = 3;

        RentalStateEnum resultEnum2 = scooterRentalService.takePaymentById(testScooterRentalId2);
        RentalStateEnum resultEnum1 = scooterRentalService.takePaymentById(testScooterRentalId);
        RentalStateEnum resultEnum3 = scooterRentalService.takePaymentById(testScooterRentalId3);

        Assert.assertEquals(RentalStateEnum.OK, resultEnum1);
        Assert.assertEquals(RentalStateEnum.BAD, resultEnum2);
        Assert.assertEquals(RentalStateEnum.CLOSED, resultEnum3);
    }

    @Test
    public void closeRentalByIdTest() {
        int testScooterRentalId = 1;
        String testUsername = "testUsername";
        int testScooterId = 1;
        int testHoursScooterRental = 1;
        float testPricePerHour = 100.0F;
        int testDiscount = 10;
        float testMoney = 100000;
        String testRentalPlaceName = "testRentalPlaceName";

        ScooterModel scooterModel = new ScooterModel();
        scooterModel.setName("testModelName");
        scooterModel.setPricePerTime(testPricePerHour);
        scooterModel.setDiscount(testDiscount);

        RentalPlace rentalPlace = new RentalPlace();
        rentalPlace.setName(testRentalPlaceName);

        Scooter scooter = new Scooter();
        scooter.setId(testScooterId);
        scooter.setRentalPlace(rentalPlace);
        scooter.setState(ScooterStateEnum.OK);

        User user = new User();
        user.setUsername(testUsername);
        user.setMoney(testMoney - (testPricePerHour * testHoursScooterRental * 3600 / pricePerTime) * (100 - testDiscount) / 100);

        ScooterRental scooterRental = new ScooterRental();
        scooterRental.setId(testScooterRentalId);
        scooterRental.setScooter(scooter);
        scooterRental.setInitRentalPlace(rentalPlace);
        scooterRental.setUser(user);
        scooterRental.setScooterTakenAt(LocalDateTime.now(fixedClock).minusHours(testHoursScooterRental));
        scooterRental.setScooterPassedAt(LocalDateTime.now(fixedClock));
        scooterRental.setFinalRentalPlace(rentalPlace);

        ScooterRentalDTO expectedScooterRentalDTO = new ScooterRentalDTO(scooterRental);

        ScooterRentalDTO resultScooterRentalDTO = scooterRentalService.closeRentalById(testScooterRentalId, new RentalPlaceNameDTO(testRentalPlaceName));

        Assert.assertEquals(expectedScooterRentalDTO, resultScooterRentalDTO);

        Assert.assertThrows(AccessDeniedException.class, () -> {
//            аренда уже закрыта
            scooterRentalService.closeRentalById(testScooterRentalId, new RentalPlaceNameDTO(testRentalPlaceName));
        });
    }

}
