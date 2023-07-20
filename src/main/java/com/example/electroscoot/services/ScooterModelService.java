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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ScooterModelService implements IScooterModelService {
    @Autowired
    private ScooterModelRepository scooterModelRepository;
    @Autowired
    private ScooterRepository scooterRepository;

    @Override
    @Transactional(readOnly = true)
    public ScooterModelDTO findById(int id) {
        return new ScooterModelDTO(scooterModelRepository.findById(id).orElse(null));
    }

    @Override
    @Transactional(readOnly = true)
    public ScooterModelDTO findByName(String name) {
        return new ScooterModelDTO(scooterModelRepository.findByName(name));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScooterModelDTO> getList() {
        return ((List<ScooterModel>) scooterModelRepository.findAll()).stream().map(ScooterModelDTO::new).toList();
    }

    @Override
    @Transactional
    public ScooterModelDTO create(CreateScooterModelDTO createData) {

        ScooterModel scooterModel = new ScooterModel();
        scooterModel.setName(createData.getName());
        scooterModel.setPricePerHour(createData.getPricePerHour());
        scooterModel.setStartPrice(createData.getStartPrice());

        return new ScooterModelDTO(scooterModelRepository.save(scooterModel));
    }

    @Override
    @Transactional
    public ScooterModelDTO updateByName(String name, UpdateScooterModelDTO updateData) {

        ScooterModel scooterModel = scooterModelRepository.findByName(name);

        String newName = updateData.getName();
        if (newName != null) {
            scooterModel.setName(newName);
        }

        Float pricePerHour = updateData.getPricePerHour();
        if (pricePerHour != null) {
            scooterModel.setPricePerHour(pricePerHour);
        }

        Float startPrice = updateData.getStartPrice();
        if (startPrice != null) {
            scooterModel.setStartPrice(startPrice);
        }

        Integer discount = updateData.getDiscount();
        if (discount != null) {
            scooterModel.setDiscount(discount);
        }

        return new ScooterModelDTO(scooterModel);
    }

    @Override
    @Transactional
    public boolean deleteByName(String name) {
        return scooterModelRepository.deleteByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScooterDTO> getScootersByName(String name) {
        return scooterModelRepository.findByName(name).getScooters().stream().map(ScooterDTO::new).toList();
    }
}
