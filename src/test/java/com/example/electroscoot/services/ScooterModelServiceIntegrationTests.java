package com.example.electroscoot.services;

import com.example.electroscoot.config.ScooterModelServiceTestContextConfiguration;
import com.example.electroscoot.dao.ScooterModelRepository;
import com.example.electroscoot.dao.ScooterRepository;
import com.example.electroscoot.dto.CreateScooterModelDTO;
import com.example.electroscoot.dto.ScooterModelDTO;
import com.example.electroscoot.dto.UpdateScooterModelDTO;
import com.example.electroscoot.entities.Scooter;
import com.example.electroscoot.entities.ScooterModel;
import com.example.electroscoot.services.interfaces.IScooterModelService;
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
@Import(ScooterModelServiceTestContextConfiguration.class)
public class ScooterModelServiceIntegrationTests {

    @Autowired
    private IScooterModelService scooterModelService;
    @MockBean
    private ScooterRepository scooterRepository;
    @MockBean
    private ScooterModelRepository scooterModelRepository;

    @Before
    public void setUp() {
        String testName = "testModelName";
        float testPricePerHour = 100.0F;
        float testStartPrice = 300.0F;

        ScooterModel scooterModel = new ScooterModel();
        scooterModel.setName(testName);
        scooterModel.setPricePerHour(testPricePerHour);
        scooterModel.setStartPrice(testStartPrice);

        Mockito.when(scooterModelRepository.save(scooterModel)).thenReturn(scooterModel);
        Mockito.when(scooterModelRepository.findByName(testName)).thenReturn(scooterModel);
    }

    @Test
    public void createTest() {
        String testName = "testModelName";
        float testPricePerHour = 100.0F;
        float testStartPrice = 300.0F;
        CreateScooterModelDTO createData = new CreateScooterModelDTO(testName, testPricePerHour, testStartPrice);

        ScooterModel scooterModel = new ScooterModel();
        scooterModel.setName(testName);
        scooterModel.setPricePerHour(testPricePerHour);
        scooterModel.setStartPrice(testStartPrice);

        ScooterModelDTO expectedScooterModelDTO = new ScooterModelDTO(scooterModel);

        ScooterModelDTO resultScooterModelDTO = scooterModelService.create(createData);

        Assert.assertEquals(expectedScooterModelDTO, resultScooterModelDTO);
    }

    @Test
    public void updateByName() {
        String testName = "testModelName";
        String otherName = "otherModelName";
        float otherPricePerHour = 150.0F;
        float testStartPrice = 300.0F;
        int discount = 10;
        UpdateScooterModelDTO updateData = new UpdateScooterModelDTO(otherName, otherPricePerHour, null, discount);

        ScooterModel scooterModel = new ScooterModel();
        scooterModel.setName(otherName);
        scooterModel.setPricePerHour(otherPricePerHour);
        scooterModel.setStartPrice(testStartPrice);
        scooterModel.setDiscount(discount);

        ScooterModelDTO expectedScooterModelDTO = new ScooterModelDTO(scooterModel);

        ScooterModelDTO resultScooterModelDTO = scooterModelService.updateByName(testName, updateData);

        Assert.assertEquals(expectedScooterModelDTO, resultScooterModelDTO);
    }
}
