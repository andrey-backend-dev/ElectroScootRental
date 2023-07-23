package com.example.electroscoot.services;

import com.example.electroscoot.config.RentalPlaceServiceTestContextConfiguration;
import com.example.electroscoot.dao.RentalPlaceRepository;
import com.example.electroscoot.dao.ScooterRepository;
import com.example.electroscoot.dto.CreateRentalPlaceDTO;
import com.example.electroscoot.dto.RentalPlaceDTO;
import com.example.electroscoot.dto.UpdateRentalPlaceDTO;
import com.example.electroscoot.entities.RentalPlace;
import com.example.electroscoot.services.interfaces.IRentalPlaceService;
import com.example.electroscoot.utils.enums.SortMethod;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@Import(RentalPlaceServiceTestContextConfiguration.class)
public class RentalPlaceServiceIntegrationTests {

    @Autowired
    private IRentalPlaceService rentalPlaceService;

    @MockBean
    private RentalPlaceRepository rentalPlaceRepository;

    @MockBean
    private ScooterRepository scooterRepository;

    @Before
    public void setUp() {
        String testName = "testName";
        String testCity = "testCity";
        String testStreet = "testStreet";
        int testHouse = 10;

        RentalPlace rentalPlace = new RentalPlace();
        rentalPlace.setName(testName);
        rentalPlace.setCity(testCity);
        rentalPlace.setStreet(testStreet);
        rentalPlace.setHouse(testHouse);

        Mockito.when(rentalPlaceRepository.save(rentalPlace)).thenReturn(rentalPlace);
        Mockito.when(rentalPlaceRepository.findByName(testName)).thenReturn(rentalPlace);
    }

    @Test
    public void createTest() {
        String testName = "testName";
        String testCity = "testCity";
        String testStreet = "testStreet";
        int testHouse = 10;

        RentalPlace rentalPlace = new RentalPlace();
        rentalPlace.setName(testName);
        rentalPlace.setCity(testCity);
        rentalPlace.setStreet(testStreet);
        rentalPlace.setHouse(testHouse);

        RentalPlaceDTO expectedRentalPlaceDTO = new RentalPlaceDTO(rentalPlace);

        CreateRentalPlaceDTO createRentalPlaceDTO = new CreateRentalPlaceDTO(testName, testCity, testStreet, testHouse);

        RentalPlaceDTO resultRentalPlaceDTO = rentalPlaceService.create(createRentalPlaceDTO);

        Assert.assertEquals(expectedRentalPlaceDTO, resultRentalPlaceDTO);

        Assert.assertThrows(IllegalArgumentException.class, () -> {
            createRentalPlaceDTO.setStreet(null);
            rentalPlaceService.create(createRentalPlaceDTO);
        });
    }

    @Test
    public void updateByNameTest() {
        String testName = "testName";
        String testStreet = "testStreet";
        int testHouse = 10;
        String otherName = "otherName";
        String otherCity = "otherCity";

        RentalPlace rentalPlace = new RentalPlace();
        rentalPlace.setName(otherName);
        rentalPlace.setCity(otherCity);
        rentalPlace.setStreet(testStreet);
        rentalPlace.setHouse(testHouse);

        UpdateRentalPlaceDTO updateRentalPlaceDTO = new UpdateRentalPlaceDTO(otherName, null, otherCity, null, null);

        RentalPlaceDTO expectedRentalPlaceDTO = new RentalPlaceDTO(rentalPlace);

        RentalPlaceDTO resultRentalPlaceDTO = rentalPlaceService.updateByName(testName, updateRentalPlaceDTO);

        Assert.assertEquals(expectedRentalPlaceDTO, resultRentalPlaceDTO);
    }
}
