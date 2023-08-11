package com.example.electroscoot.services;

import com.example.electroscoot.configs.ScooterServiceTestContextConfiguration;
import com.example.electroscoot.dao.RentalPlaceRepository;
import com.example.electroscoot.dao.ScooterModelRepository;
import com.example.electroscoot.dao.ScooterRepository;
import com.example.electroscoot.dao.UserRepository;
import com.example.electroscoot.dto.CreateScooterDTO;
import com.example.electroscoot.dto.ScooterDTO;
import com.example.electroscoot.dto.UpdateScooterDTO;
import com.example.electroscoot.entities.RentalPlace;
import com.example.electroscoot.entities.Scooter;
import com.example.electroscoot.entities.ScooterModel;
import com.example.electroscoot.services.interfaces.IScooterService;
import com.example.electroscoot.utils.enums.ScooterStateEnum;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@Import(ScooterServiceTestContextConfiguration.class)
public class ScooterServiceIntegrationTests {

    @Autowired
    private IScooterService scooterService;

    @MockBean
    private ScooterRepository scooterRepository;

    @MockBean
    private ScooterModelRepository scooterModelRepository;

    @MockBean
    private RentalPlaceRepository rentalPlaceRepository;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setUp() {
        int testId = 1;
        String rentalPlaceName = "testRentalName";
        String modelName = "testModelName";

        RentalPlace rentalPlace = new RentalPlace();
        rentalPlace.setName(rentalPlaceName);

        ScooterModel model = new ScooterModel();
        model.setName(modelName);

        Scooter scooterWithoutId = new Scooter();
        scooterWithoutId.setRentalPlace(rentalPlace);
        scooterWithoutId.setModel(model);

        Scooter scooter = new Scooter();
        scooter.setId(testId);
        scooter.setRentalPlace(rentalPlace);
        scooter.setModel(model);

        Mockito.when(scooterModelRepository.findByName(modelName)).thenReturn(Optional.of(model));
        Mockito.when(rentalPlaceRepository.findByName(rentalPlaceName)).thenReturn(Optional.of(rentalPlace));
        Mockito.when(scooterRepository.save(scooterWithoutId)).thenReturn(scooter);
        Mockito.when(scooterRepository.findById(testId)).thenReturn(Optional.of(scooter));
    }

    @Test
    public void createTest() {
        int testId = 1;
        String rentalPlaceName = "testRentalName";
        String modelName = "testModelName";

        RentalPlace rentalPlace = new RentalPlace();
        rentalPlace.setName(rentalPlaceName);

        ScooterModel model = new ScooterModel();
        model.setName(modelName);

        Scooter scooter = new Scooter();
        scooter.setId(testId);
        scooter.setRentalPlace(rentalPlace);
        scooter.setModel(model);
        scooter.setState(ScooterStateEnum.OK);

        ScooterDTO expectedScooterDTO = new ScooterDTO(scooter);

        CreateScooterDTO createScooterDTO = new CreateScooterDTO(rentalPlaceName, modelName, null);

        ScooterDTO resultScooterDTO = scooterService.create(createScooterDTO);

        Assert.assertEquals(expectedScooterDTO, resultScooterDTO);
    }

    @Test
    public void updateByIdTest() {
        int testId = 1;
        String rentalPlaceName = "testRentalName";
        String modelName = "testModelName";

        RentalPlace rentalPlace = new RentalPlace();
        rentalPlace.setName(rentalPlaceName);

        ScooterModel model = new ScooterModel();
        model.setName(modelName);

        Scooter scooter = new Scooter();
        scooter.setId(testId);
        scooter.setRentalPlace(rentalPlace);
        scooter.setModel(model);
        scooter.setState(ScooterStateEnum.BROKEN);

        ScooterDTO expectedScooterDTO = new ScooterDTO(scooter);

        UpdateScooterDTO updateData = new UpdateScooterDTO(rentalPlaceName, modelName, ScooterStateEnum.BROKEN);

        ScooterDTO resultScooterDTO = scooterService.updateById(testId, updateData);

        Assert.assertEquals(expectedScooterDTO, resultScooterDTO);
    }

}
