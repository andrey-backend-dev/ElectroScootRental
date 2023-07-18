package com.example.electroscoot.services;

import com.example.electroscoot.dao.RentalPlaceRepository;
import com.example.electroscoot.dao.ScooterRepository;
import com.example.electroscoot.dto.CreateRentalPlaceDTO;
import com.example.electroscoot.dto.UpdateRentalPlaceDTO;
import com.example.electroscoot.entities.RentalPlace;
import com.example.electroscoot.entities.Role;
import com.example.electroscoot.entities.Scooter;
import com.example.electroscoot.entities.ScooterModel;
import com.example.electroscoot.services.interfaces.IRentalPlaceService;
import com.example.electroscoot.services.interfaces.IRoleService;
import com.example.electroscoot.services.interfaces.IScooterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RentalPlaceService implements IRentalPlaceService {
    @Autowired
    private ScooterRepository scooterRepository;
    @Autowired
    private RentalPlaceRepository rentalPlaceRepository;

    @Override
    @Transactional(readOnly = true)
    public RentalPlace findById(int id) {
        return rentalPlaceRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public RentalPlace findByName(String name) {
        return rentalPlaceRepository.findByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RentalPlace> getList() {
        return (List<RentalPlace>) rentalPlaceRepository.findAll();
    }

    @Override
    @Transactional
    public RentalPlace create(CreateRentalPlaceDTO createData) {

        String city = createData.getCity();
        String street = createData.getStreet();
        Integer house = createData.getHouse();

        if (city != null && street != null && house != null) {
            RentalPlace rentalPlace = new RentalPlace();
            rentalPlace.setName(createData.getName());
            rentalPlace.setAddress(String.join(",", city, street, house.toString()));

            return rentalPlaceRepository.save(rentalPlace);
        }

//        вызвать ошибку

        return null;
    }

    @Override
    @Transactional
    public RentalPlace updateByName(String name, UpdateRentalPlaceDTO updateData) {

        RentalPlace rentalPlace = findByName(name);
        String[] newAddress = new String[3];
        String[] oldAddress = rentalPlace.getAddress().split(",");

        String newName = updateData.getName();
        if (newName != null) {
            rentalPlace.setName(newName);
        }

        Integer rating = updateData.getRating();
        if (rating != null) {
            rentalPlace.setRating(rating);
        }

        String city = updateData.getCity();
        if (city != null) {
            newAddress[0] = city;
        } else {
            newAddress[0] = oldAddress[0];
        }

        String street = updateData.getStreet();
        if (street != null) {
            newAddress[1] = street;
        } else {
            newAddress[1] = oldAddress[1];
        }

        Integer house = updateData.getHouse();
        if (house != null) {
            newAddress[2] = house.toString();
        } else {
            newAddress[2] = oldAddress[2];
        }

        rentalPlace.setAddress(String.join(",", newAddress));

        return rentalPlaceRepository.save(rentalPlace);
    }

    @Override
    @Transactional
    public boolean deleteByName(String name) {
        return rentalPlaceRepository.deleteByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RentalPlace> getListSortedByAddresses() {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Scooter> getScootersByName(String name) {
        return findByName(name).getScooters();
    }

    @Override
    @Transactional
    public boolean addScooterByName(String name, int scooterId) {

        RentalPlace rentalPlace = findByName(name);
        Scooter scooter = scooterRepository.findById(scooterId).orElse(null);
        List<Scooter> scooters = rentalPlace.getScooters();

        if (!scooters.contains(scooter)) {
            return scooters.add(scooter);
        }

        return false;
    }

    @Override
    @Transactional
    public boolean removeScooterByName(String name, int scooterId) {

        RentalPlace rentalPlace = findByName(name);
        Scooter scooter = scooterRepository.findById(scooterId).orElse(null);
        List<Scooter> scooters = rentalPlace.getScooters();

        if (scooters.contains(scooter)) {
            return scooters.remove(scooter);
        }

        return false;
    }

}
