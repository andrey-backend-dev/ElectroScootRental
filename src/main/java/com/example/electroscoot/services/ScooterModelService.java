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
import com.example.electroscoot.utils.mappers.ScooterMapper;
import com.example.electroscoot.utils.mappers.ScooterModelMapper;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ScooterModelService implements IScooterModelService {
    private final ScooterModelRepository scooterModelRepository;
    private final ScooterModelMapper scooterModelMapper;
    private final ScooterMapper scooterMapper;

    @Override
    @Transactional(readOnly = true)
    public ScooterModelDTO findById(@Positive(message = "Id must be more than zero.") int id) {
        return scooterModelMapper.scooterModelToScooterModelDto(scooterModelRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("The scooter model with id " + id + " does not exist.");
        }));
    }

    @Override
    @Transactional(readOnly = true)
    public ScooterModelDTO findByName(@NotBlank(message = "Name is mandatory.") String name) {
        return scooterModelMapper.scooterModelToScooterModelDto(scooterModelRepository.findByName(name).orElseThrow(() -> {
            return new IllegalArgumentException("The scooter model with name " + name + " does not exist.");
        }));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScooterModelDTO> getList() {
        return scooterModelMapper.scooterModelToScooterModelDto(scooterModelRepository.findAll());
    }

    @Override
    @Transactional
    public ScooterModelDTO create(@Valid CreateScooterModelDTO createData) {

        ScooterModel scooterModel = scooterModelMapper.scooterModelDtoToScooterModel(createData);

        return scooterModelMapper.scooterModelToScooterModelDto(scooterModelRepository.save(scooterModel));
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

        return scooterModelMapper.scooterModelToScooterModelDto(scooterModel);
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
        return scooterMapper.scooterToScooterDto(scooterModel.getScooters());
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
