package com.example.electroscoot.services;

import com.example.electroscoot.dao.ScooterModelRepository;
import com.example.electroscoot.dao.ScooterRepository;
import com.example.electroscoot.dto.CreateScooterModelDTO;
import com.example.electroscoot.dto.ScooterDTO;
import com.example.electroscoot.dto.ScooterModelDTO;
import com.example.electroscoot.dto.UpdateScooterModelDTO;
import com.example.electroscoot.entities.Scooter;
import com.example.electroscoot.entities.ScooterModel;
import com.example.electroscoot.services.interfaces.IScooterModelService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class ScooterModelService implements IScooterModelService {
    @Autowired
    private ScooterModelRepository scooterModelRepository;
    @Autowired
    private ScooterRepository scooterRepository;

    @Override
    @Transactional(readOnly = true)
    public ScooterModelDTO findById(@Positive(message = "Id must be more than zero.") int id) {
        return new ScooterModelDTO(scooterModelRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("The scooter model with id " + id + " does not exist.");
        }));
    }

    @Override
    @Transactional(readOnly = true)
    public ScooterModelDTO findByName(@NotBlank(message = "Name is mandatory.") String name) {
        return new ScooterModelDTO(scooterModelRepository.findByName(name).orElseThrow(() -> {
            return new IllegalArgumentException("The scooter model with name " + name + " does not exist.");
        }));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScooterModelDTO> getList() {
        return ((List<ScooterModel>) scooterModelRepository.findAll()).stream().map(ScooterModelDTO::new).toList();
    }

    @Override
    @Transactional
    public ScooterModelDTO create(@Valid CreateScooterModelDTO createData) {

        ScooterModel scooterModel = new ScooterModel();
        scooterModel.setName(createData.getName());
        scooterModel.setPricePerTime(createData.getPricePerTime());
        scooterModel.setStartPrice(createData.getStartPrice());

        return new ScooterModelDTO(scooterModelRepository.save(scooterModel));
    }

    @Override
    @Transactional
    public ScooterModelDTO updateByName(@NotBlank(message = "Name is mandatory.") String name, UpdateScooterModelDTO updateData) {

        ScooterModel scooterModel = scooterModelRepository.findByName(name).orElseThrow(() -> {
            return new IllegalArgumentException("The scooter model with name " + name + " does not exist.");
        });

        String newName = updateData.getName();
        if (newName != null) {
            scooterModel.setName(getNameIfValid(newName));
        }

        Float pricePerTime = updateData.getPricePerTime();
        if (pricePerTime != null) {
            scooterModel.setPricePerTime(getPricePerTimeIfValid(pricePerTime));
        }

        Float startPrice = updateData.getStartPrice();
        if (startPrice != null) {
            scooterModel.setStartPrice(getStartPriceIfValid(startPrice));
        }

        Integer discount = updateData.getDiscount();
        if (discount != null) {
            scooterModel.setDiscount(getDiscountIfValid(discount));
        }

        return new ScooterModelDTO(scooterModel);
    }

    @Override
    @Transactional
    public boolean deleteByName(@NotBlank(message = "Name is mandatory.") String name) {
        scooterModelRepository.findByName(name).orElseThrow(() -> {
            return new IllegalArgumentException("Scooter model with name " + name + " does not exist.");
        });

        scooterModelRepository.deleteByName(name);

        return scooterModelRepository.findByName(name).orElse(null) == null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScooterDTO> getScootersByName(@NotBlank(message = "Name is mandatory.") String name) {
        ScooterModel scooterModel = scooterModelRepository.findByName(name).orElseThrow(() -> {
            return new IllegalArgumentException("The scooter model with name " + name + " does not exist.");
        });
        return scooterModel.getScooters().stream().map(ScooterDTO::new).toList();
    }

    private String getNameIfValid(String newName) {
        if (!newName.isBlank()) {
            return newName;
        }
        throw new ConstraintViolationException("Name can not be blank.", null);
    }

    private float getPricePerTimeIfValid(float pricePerTime) {
        if (pricePerTime > 0)
            return pricePerTime;
        throw new ConstraintViolationException("Price per time must be more than zero.", null);
    }

    private float getStartPriceIfValid(float startPrice) {
        if (startPrice >= 0) {
            return startPrice;
        }
        throw new ConstraintViolationException("Start price can not be less than zero.", null);
    }

    private int getDiscountIfValid(int discount) {
        if (discount >= 0 && discount <= 100) {
            return discount;
        }
        throw new ConstraintViolationException("Discount must be in range of 0 to 100.", null);
    }
}
