package com.example.electroscoot.services;

import com.example.electroscoot.dao.ScooterModelRepository;
import com.example.electroscoot.dao.ScooterRepository;
import com.example.electroscoot.dto.CreateScooterModelDTO;
import com.example.electroscoot.dto.UpdateScooterModelDTO;
import com.example.electroscoot.entities.RentalPlace;
import com.example.electroscoot.entities.Scooter;
import com.example.electroscoot.entities.ScooterModel;
import com.example.electroscoot.services.interfaces.IRentalPlaceService;
import com.example.electroscoot.services.interfaces.IScooterModelService;
import com.example.electroscoot.services.interfaces.IScooterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScooterModelService implements IScooterModelService {
    @Autowired
    private ScooterModelRepository scooterModelRepository;
    @Autowired
    private ScooterRepository scooterRepository;

    @Override
    @Transactional(readOnly = true)
    public ScooterModel findById(int id) {
        return scooterModelRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public ScooterModel findByName(String name) {
        return scooterModelRepository.findByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScooterModel> getList() {
        return (List<ScooterModel>) scooterModelRepository.findAll();
    }

    @Override
    @Transactional
    public ScooterModel create(CreateScooterModelDTO createData) {

        ScooterModel scooterModel = new ScooterModel();
        scooterModel.setName(createData.getName());
        scooterModel.setPricePerHour(createData.getPricePerHour());
        scooterModel.setStartPrice(createData.getStartPrice());

        return scooterModelRepository.save(scooterModel);
    }

    @Override
    @Transactional
    public ScooterModel updateByName(String name, UpdateScooterModelDTO updateData) {

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

        return scooterModel;
    }

    @Override
    @Transactional
    public boolean deleteByName(String name) {
        return scooterModelRepository.deleteByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Scooter> getScootersByName(String name) {
        return findByName(name).getScooters();
    }

    @Override
    @Transactional
    public boolean addScooterByName(String name, int scooterId) {

        ScooterModel scooterModel = findByName(name);
        Scooter scooter = scooterRepository.findById(scooterId).orElse(null);
        List<Scooter> scooters = scooterModel.getScooters();

        if (!scooters.contains(scooter)) {
            return scooters.add(scooter);
        }

        return false;
    }

    @Override
    @Transactional
    public boolean removeScooterByName(String name, int scooterId) {
        ScooterModel scooterModel = findByName(name);
        Scooter scooter = scooterRepository.findById(scooterId).orElse(null);
        List<Scooter> scooters = scooterModel.getScooters();

        if (scooters.contains(scooter)) {
            return scooters.remove(scooter);
        }

        return false;
    }
}
