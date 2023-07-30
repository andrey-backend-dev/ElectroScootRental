package com.example.electroscoot.services;

import com.example.electroscoot.dao.RentalPlaceRepository;
import com.example.electroscoot.dao.ScooterModelRepository;
import com.example.electroscoot.dao.ScooterRepository;
import com.example.electroscoot.dto.CreateScooterDTO;
import com.example.electroscoot.dto.ScooterDTO;
import com.example.electroscoot.dto.UpdateScooterDTO;
import com.example.electroscoot.entities.RentalPlace;
import com.example.electroscoot.entities.Scooter;
import com.example.electroscoot.entities.ScooterModel;
import com.example.electroscoot.services.interfaces.IScooterService;
import com.example.electroscoot.utils.enums.ScooterStateEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public ScooterDTO findById(@Positive(message = "Id must be more than zero.") int id) {
        return new ScooterDTO(scooterRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("The scooter with id " + id + " does not exist.");
        }));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScooterDTO> getList() {
        return ((List<Scooter>) scooterRepository.findAll()).stream().map(ScooterDTO::new).toList();
    }

    @Override
    @Transactional
    public ScooterDTO create(@Valid CreateScooterDTO createData) {

        Scooter scooter = new Scooter();

        scooter.setModel(scooterModelRepository.findByName(createData.getModel()).orElseThrow(() -> {
            return new IllegalArgumentException("The scooter model with name " + createData.getModel() + " does not exist.");
        }));

        if (createData.getState() != null) {
            scooter.setState(createData.getState());
        } else {
            scooter.setState(ScooterStateEnum.OK);
        }

        if (createData.getRentalPlaceName() != null) {
            RentalPlace rentalPlace = rentalPlaceRepository.findByName(createData.getRentalPlaceName()).orElseThrow(() -> {
                return new IllegalArgumentException("The rental place with name " + createData.getRentalPlaceName() + " does not exist.");
            });
            scooter.setRentalPlace(rentalPlace);
        }

        return new ScooterDTO(scooterRepository.save(scooter));
    }

    @Override
    @Transactional
    public boolean deleteById(@Positive(message = "Id must be more than zero.") int id) {
        scooterRepository.findById(id).orElseThrow(() -> {
           return new IllegalArgumentException("Scooter with id " + id + " does not exist.");
        });

        scooterRepository.deleteById(id);

        return scooterRepository.findById(id).orElse(null) == null;
    }

    @Override
    @Transactional
    public ScooterDTO updateById(UpdateScooterDTO updateData) {

        Scooter scooter = scooterRepository.findById(updateData.getId()).orElseThrow(() -> {
            return new IllegalArgumentException("The scooter with id " + updateData.getId() + " does not exist.");
        });

        if (updateData.getRentalPlaceName() != null) {
            RentalPlace rentalPlace = rentalPlaceRepository.findByName(updateData.getRentalPlaceName()).orElseThrow(() -> {
                return new IllegalArgumentException("The rental place with name " + updateData.getRentalPlaceName() + " does not exist.");
            });
            scooter.setRentalPlace(rentalPlace);
        }

        if (updateData.getModel() != null) {
            ScooterModel scooterModel = scooterModelRepository.findByName(updateData.getModel()).orElseThrow(() -> {
                return new IllegalArgumentException("The scooter model with name " + updateData.getModel() + " does not exist.");
            });
            scooter.setModel(scooterModel);
        }

        ScooterStateEnum state = updateData.getState();
        if (state != null) {
            scooter.setState(state);
        }

        return new ScooterDTO(scooter);
    }
}
