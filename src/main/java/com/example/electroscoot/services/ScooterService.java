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
import com.example.electroscoot.utils.enums.OrderEnum;
import com.example.electroscoot.utils.enums.ScooterStateEnum;
import com.example.electroscoot.utils.enums.SortMethod;
import com.example.electroscoot.utils.maps.ScooterStateMap;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
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
    public List<ScooterDTO> getList(@NotNull(message = "Sort method is mandatory.") SortMethod sortMethod,
                                    @NotNull(message = "Ordering is mandatory.") OrderEnum ordering,
                                    @NotNull(message = "Scooter-filter is mandatory.") ScooterStateEnum state) {
        if (sortMethod != SortMethod.NULL && ordering == OrderEnum.NULL) {
            ordering = OrderEnum.ASC;
        }

        if (state == ScooterStateEnum.NULL) {
            if (sortMethod == SortMethod.NULL) {
                return ((List<Scooter>) scooterRepository.findAll()).stream().map(ScooterDTO::new).toList();
            } else if (sortMethod == SortMethod.STATE) {
                if (ordering == OrderEnum.ASC) {
                    return scooterRepository.findByOrderByStateAsc().stream().map(ScooterDTO::new).toList();
                } else {
                    return scooterRepository.findByOrderByStateDesc().stream().map(ScooterDTO::new).toList();
                }
            }
            throw new ConstraintViolationException("Invalid sort method.", null);
        } else {
            return scooterRepository.findByState(state).stream().map(ScooterDTO::new).toList();
        }
    }

    @Override
    @Transactional
    public ScooterDTO create(@Valid CreateScooterDTO createData) {

        Scooter scooter = new Scooter();

        scooter.setModel(scooterModelRepository.findByName(createData.getModel()).orElseThrow(() -> {
            return new IllegalArgumentException("The scooter model with name " + createData.getModel() + " does not exist.");
        }));


        if (createData.getState() != null) {
            ScooterStateEnum scooterState = ScooterStateMap.getScooterStateByName(createData.getState());
            if (scooterState != ScooterStateEnum.OK && scooterState != ScooterStateEnum.BROKEN)
                throw new IllegalArgumentException("Scooter state, while creating, can be only OK/BROKEN.");
            scooter.setState(scooterState);
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
    public ScooterDTO updateById(int id, UpdateScooterDTO updateData) {

        Scooter scooter = scooterRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("The scooter with id " + id + " does not exist.");
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

        if (updateData.getState() != null) {
            ScooterStateEnum state = ScooterStateMap.getScooterStateByName(updateData.getState());
            if (state == ScooterStateEnum.NULL)
                throw new IllegalArgumentException("No such state as " + updateData.getState() + ".");
            else if (state == ScooterStateEnum.RENTED)
                throw new IllegalArgumentException("You can not directly set the RENTED state.");
            scooter.setState(ScooterStateMap.getScooterStateByName(updateData.getState()));
        }

        return new ScooterDTO(scooter);
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
}
