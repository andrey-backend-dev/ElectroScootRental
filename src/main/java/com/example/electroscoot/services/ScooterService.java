package com.example.electroscoot.services;

import com.example.electroscoot.dao.RentalPlaceRepository;
import com.example.electroscoot.dao.ScooterModelRepository;
import com.example.electroscoot.dao.ScooterRentalRepository;
import com.example.electroscoot.dao.ScooterRepository;
import com.example.electroscoot.dao.UserRepository;
import com.example.electroscoot.dto.CreateScooterDTO;
import com.example.electroscoot.dto.ScooterDTO;
import com.example.electroscoot.dto.UpdateScooterDTO;
import com.example.electroscoot.entities.RentalPlace;
import com.example.electroscoot.entities.Scooter;
import com.example.electroscoot.entities.ScooterModel;
import com.example.electroscoot.entities.ScooterRental;
import com.example.electroscoot.entities.User;
import com.example.electroscoot.services.interfaces.IScooterService;
import com.example.electroscoot.utils.enums.StateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ScooterService implements IScooterService {
    @Autowired
    private ScooterRepository scooterRepository;
    @Autowired
    private ScooterModelRepository scooterModelRepository;
    @Autowired
    private RentalPlaceRepository rentalPlaceRepository;

    @Override
    @Transactional(readOnly = true)
    public ScooterDTO findById(int id) {
        return new ScooterDTO(scooterRepository.findById(id).orElse(null));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScooterDTO> getList() {
        return ((List<Scooter>) scooterRepository.findAll()).stream().map(ScooterDTO::new).toList();
    }

    @Override
    @Transactional
    public ScooterDTO create(CreateScooterDTO createData) {

        Scooter scooter = new Scooter();

//        указывать модель обязательно, поэтому не проверяем
        scooter.setModel(scooterModelRepository.findByName(createData.getModel()));

        if (createData.getState() != null) {
            scooter.setState(createData.getState());
        }

//        указывать точку проката необязательно, поэтому проверяем

        if (createData.getRentalPlaceName() != null) {
            RentalPlace rentalPlace = rentalPlaceRepository.findByName(createData.getRentalPlaceName());
            scooter.setRentalPlace(rentalPlace);
        }

        return new ScooterDTO(scooterRepository.save(scooter));
    }

    @Override
    @Transactional
    public boolean deleteById(int id) {
        scooterRepository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public ScooterDTO updateById(UpdateScooterDTO updateData) {

        Scooter scooter = scooterRepository.findById(updateData.getId()).orElse(null);

        if (updateData.getRentalPlaceName() != null) {
            RentalPlace rentalPlace = rentalPlaceRepository.findByName(updateData.getRentalPlaceName());
            scooter.setRentalPlace(rentalPlace);
        }

        if (updateData.getModel() != null) {
            ScooterModel scooterModel = scooterModelRepository.findByName(updateData.getModel());
            scooter.setModel(scooterModel);
        }

        StateEnum state = updateData.getState();
        if (state != null) {
            scooter.setState(state);
        }

        return new ScooterDTO(scooter);
    }
}
