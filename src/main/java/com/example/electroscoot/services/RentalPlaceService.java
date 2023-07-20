package com.example.electroscoot.services;

import com.example.electroscoot.dao.RentalPlaceRepository;
import com.example.electroscoot.dao.ScooterRepository;
import com.example.electroscoot.dto.CreateRentalPlaceDTO;
import com.example.electroscoot.dto.RentalPlaceDTO;
import com.example.electroscoot.dto.ScooterDTO;
import com.example.electroscoot.dto.UpdateRentalPlaceDTO;
import com.example.electroscoot.entities.RentalPlace;
import com.example.electroscoot.services.interfaces.IRentalPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RentalPlaceService implements IRentalPlaceService {
    @Autowired
    private ScooterRepository scooterRepository;
    @Autowired
    private RentalPlaceRepository rentalPlaceRepository;

    @Override
    @Transactional(readOnly = true)
    public RentalPlaceDTO findById(int id) {
        return new RentalPlaceDTO(rentalPlaceRepository.findById(id).orElse(null));
    }

    @Override
    @Transactional(readOnly = true)
    public RentalPlaceDTO findByName(String name) {
        return new RentalPlaceDTO(rentalPlaceRepository.findByName(name));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RentalPlaceDTO> getList() {
        return ((List<RentalPlace>) rentalPlaceRepository.findAll()).stream().map(RentalPlaceDTO::new).toList();
    }

    @Override
    @Transactional
    public RentalPlaceDTO create(CreateRentalPlaceDTO createData) {

        String city = createData.getCity();
        String street = createData.getStreet();
        Integer house = createData.getHouse();

        if (city != null && street != null && house != null) {
            RentalPlace rentalPlace = new RentalPlace();
            rentalPlace.setName(createData.getName());
            rentalPlace.setAddress(String.join(",", city, street, house.toString()));

            return new RentalPlaceDTO(rentalPlaceRepository.save(rentalPlace));
        }

//        вызвать ошибку

        return null;
    }

    @Override
    @Transactional
    public RentalPlaceDTO updateByName(String name, UpdateRentalPlaceDTO updateData) {

        RentalPlace rentalPlace = rentalPlaceRepository.findByName(name);
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

        return new RentalPlaceDTO(rentalPlaceRepository.save(rentalPlace));
    }

    @Override
    @Transactional
    public boolean deleteByName(String name) {
        rentalPlaceRepository.deleteByName(name);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RentalPlaceDTO> getListSortedByAddresses() {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScooterDTO> getScootersByName(String name) {
        return rentalPlaceRepository.findByName(name).getScooters().stream().map(ScooterDTO::new).toList();
    }

}
