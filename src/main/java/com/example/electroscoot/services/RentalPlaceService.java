package com.example.electroscoot.services;

import com.example.electroscoot.dao.RentalPlaceRepository;
import com.example.electroscoot.dao.ScooterRepository;
import com.example.electroscoot.dto.CreateRentalPlaceDTO;
import com.example.electroscoot.dto.RentalPlaceDTO;
import com.example.electroscoot.dto.ScooterDTO;
import com.example.electroscoot.dto.UpdateRentalPlaceDTO;
import com.example.electroscoot.entities.RentalPlace;
import com.example.electroscoot.services.interfaces.IRentalPlaceService;
import com.example.electroscoot.utils.enums.SortMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
        return new RentalPlaceDTO(rentalPlaceRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("The rental place with id " + id + " does not exist.");
        }));
    }

    @Override
    @Transactional(readOnly = true)
    public RentalPlaceDTO findByName(String name) {
        return new RentalPlaceDTO(rentalPlaceRepository.findByName(name).orElseThrow(() -> {
            return new IllegalArgumentException("The rental place with name " + name + " does not exist.");
        }));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RentalPlaceDTO> getList(SortMethod sortMethod) {
        if (sortMethod.getName().equals("address")) {
            return rentalPlaceRepository.findByOrderByCityAscStreetAscHouseAsc().stream().
                    map(RentalPlaceDTO::new).toList();
        } else if (sortMethod.getName().equals("null")) {
            return ((List<RentalPlace>) rentalPlaceRepository.findAll()).stream().map(RentalPlaceDTO::new).toList();
        }
        throw new IllegalArgumentException("invalid sort method");
    }

    @Override
    @Transactional(readOnly = true)
    public List<RentalPlaceDTO> getListByCity(String city) {
        return rentalPlaceRepository.findByCity(city).stream().map(RentalPlaceDTO::new).toList();
    }

    @Override
    @Transactional
    public RentalPlaceDTO create(CreateRentalPlaceDTO createData) {

        String city = createData.getCity();
        String street = createData.getStreet();
        Integer house = createData.getHouse();

        if (city != null && street != null) {

            RentalPlace rentalPlace = new RentalPlace();
            rentalPlace.setName(createData.getName());
            rentalPlace.setCity(createData.getCity());
            rentalPlace.setStreet(createData.getStreet());
            rentalPlace.setHouse(createData.getHouse());

            return new RentalPlaceDTO(rentalPlaceRepository.save(rentalPlace));
        }

        throw new IllegalArgumentException("Address is invalid. City and street must be declared.");
    }

    @Override
    @Transactional
    public RentalPlaceDTO updateByName(String name, UpdateRentalPlaceDTO updateData) {

        RentalPlace rentalPlace = rentalPlaceRepository.findByName(name).orElseThrow(() -> {
            return new IllegalArgumentException("The rental place with name " + name + " does not exist.");
        });

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
            rentalPlace.setCity(city);
        }

        String street = updateData.getStreet();
        if (street != null) {
            rentalPlace.setStreet(street);
        }

        Integer house = updateData.getHouse();
        if (house != null) {
            rentalPlace.setHouse(house);
        }

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
    public List<ScooterDTO> getScootersByName(String name) {
        RentalPlace rentalPlace = rentalPlaceRepository.findByName(name).orElseThrow(() -> {
            return new IllegalArgumentException("The rental place with name " + name + " does not exist.");
        });

        return rentalPlace.getScooters().stream().map(ScooterDTO::new).toList();
    }

}
