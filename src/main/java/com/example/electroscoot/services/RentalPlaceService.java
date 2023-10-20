package com.example.electroscoot.services;

import com.example.electroscoot.dao.RentalPlaceRepository;
import com.example.electroscoot.dao.ScooterRepository;
import com.example.electroscoot.dto.CreateRentalPlaceDTO;
import com.example.electroscoot.dto.RentalPlaceDTO;
import com.example.electroscoot.dto.ScooterDTO;
import com.example.electroscoot.dto.UpdateRentalPlaceDTO;
import com.example.electroscoot.entities.RentalPlace;
import com.example.electroscoot.services.interfaces.IRentalPlaceService;
import com.example.electroscoot.utils.enums.OrderEnum;
import com.example.electroscoot.utils.enums.SortMethod;
import com.example.electroscoot.utils.mappers.RentalPlaceMapper;
import com.example.electroscoot.utils.mappers.ScooterMapper;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RentalPlaceService implements IRentalPlaceService {
    private final RentalPlaceRepository rentalPlaceRepository;
    private final RentalPlaceMapper rentalPlaceMapper;
    private final ScooterMapper scooterMapper;

    @Override
    @Transactional(readOnly = true)
    public RentalPlaceDTO findById(@Positive(message = "Id must be more than zero.") int id) {
        return rentalPlaceMapper.rentalPlaceToRentalPlaceDto(rentalPlaceRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("The rental place with id " + id + " does not exist.");
        }));
    }

    @Override
    @Transactional(readOnly = true)
    public RentalPlaceDTO findByName(@NotBlank(message = "Name is mandatory.") String name) {
        return rentalPlaceMapper.rentalPlaceToRentalPlaceDto(rentalPlaceRepository.findByName(name).orElseThrow(() -> {
            return new IllegalArgumentException("The rental place with name " + name + " does not exist.");
        }));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RentalPlaceDTO> findAll(@NotNull(message = "Sort method is mandatory.") SortMethod sortMethod,
                                        @NotNull(message = "Ordering is mandatory.") OrderEnum ordering, String city) {
        if (sortMethod != SortMethod.NULL && ordering == OrderEnum.NULL) {
            ordering = OrderEnum.ASC;
        }

        if (sortMethod == SortMethod.ADDRESS) {
            if (city == null || city.isBlank()) {
                return returnSortedConsideringOrdering(ordering);
            } else {
                return returnSortedConsideringOrderingAndCity(ordering, city);
            }
        } else if (sortMethod == SortMethod.NULL) {
            if (city == null || city.isBlank()) {
                return rentalPlaceMapper.rentalPlaceToRentalPlaceDto(rentalPlaceRepository.findAll());
            } else {
                return rentalPlaceMapper.rentalPlaceToRentalPlaceDto(rentalPlaceRepository.findByCity(city));
            }
        }

        throw new ConstraintViolationException("Invalid sort method.", null);
    }

    @Override
    @Transactional
    public RentalPlaceDTO create(@Valid CreateRentalPlaceDTO createData) {


        if (createData.getHouse() != null && createData.getHouse() <= 0)
            throw new ConstraintViolationException("House must be more than zero.", null);


        RentalPlace rentalPlace = rentalPlaceMapper.createRentalPlaceDtoToRentalPlace(createData);

        return rentalPlaceMapper.rentalPlaceToRentalPlaceDto(rentalPlaceRepository.save(rentalPlace));
    }

    @Override
    @Transactional
    public RentalPlaceDTO updateByName(@NotBlank(message = "Name is mandatory.") String name, UpdateRentalPlaceDTO updateData) {

        RentalPlace rentalPlace = rentalPlaceRepository.findByName(name).orElseThrow(() -> {
            return new IllegalArgumentException("The rental place with name " + name + " does not exist.");
        });

        String newName = updateData.getName();
        if (newName != null) {
            rentalPlace.setName(getNameIfValid(newName));
        }

        Integer rating = updateData.getRating();
        if (rating != null) {
            rentalPlace.setRating(getRatingIfValid(rating));
        }

        String city = updateData.getCity();
        if (city != null) {
            rentalPlace.setCity(getCityIfValid(city));
        }

        String street = updateData.getStreet();
        if (street != null) {
            rentalPlace.setStreet(getStreetIfValid(street));
        }

        Integer house = updateData.getHouse();
        if (house != null) {
            rentalPlace.setHouse(getHouseIfValid(house));
        }

        return rentalPlaceMapper.rentalPlaceToRentalPlaceDto(rentalPlaceRepository.save(rentalPlace));
    }

    @Override
    @Transactional
    public boolean deleteByName(@NotBlank(message = "Name is mandatory.") String name) {
        rentalPlaceRepository.findByName(name).orElseThrow(() -> {
            return new IllegalArgumentException("Rental place with name " + name + " does not exist.");
        });

        rentalPlaceRepository.deleteByName(name);

        return rentalPlaceRepository.findByName(name).orElse(null) == null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScooterDTO> findScootersByName(@NotBlank(message = "Name is mandatory.") String name) {
        RentalPlace rentalPlace = rentalPlaceRepository.findByName(name).orElseThrow(() -> {
            return new IllegalArgumentException("The rental place with name " + name + " does not exist.");
        });

        return scooterMapper.scooterToScooterDto(rentalPlace.getScooters());
    }

    private List<RentalPlaceDTO> returnSortedConsideringOrderingAndCity(OrderEnum ordering, String city) {
        if (ordering == OrderEnum.ASC) {
            return rentalPlaceMapper.rentalPlaceToRentalPlaceDto(rentalPlaceRepository.findByCityOrderByCityAscStreetAscHouseAsc(city));
        } else {
            return rentalPlaceMapper.rentalPlaceToRentalPlaceDto(rentalPlaceRepository.findByCityOrderByCityDescStreetDescHouseDesc(city));
        }
    }

    private List<RentalPlaceDTO> returnSortedConsideringOrdering(OrderEnum ordering) {
        if (ordering == OrderEnum.ASC)
            return rentalPlaceMapper.rentalPlaceToRentalPlaceDto(rentalPlaceRepository.findByOrderByCityAscStreetAscHouseAsc());
        else
            return rentalPlaceMapper.rentalPlaceToRentalPlaceDto(rentalPlaceRepository.findByOrderByCityDescStreetDescHouseDesc());
    }

    private Integer getHouseIfValid(Integer house) {
        if (house > 0) {
            return house;
        }
        throw new ConstraintViolationException("House can not be less than zero.", null);
    }

    private String getStreetIfValid(String street) {
        if (!street.isBlank()) {
            return street;
        }
        throw new ConstraintViolationException("Street can not be blank", null);
    }

    private String getCityIfValid(String city) {
        if (!city.isBlank())
            return city;
        throw new ConstraintViolationException("City can not be blank.", null);
    }

    private int getRatingIfValid(Integer rating) {
        if (rating > 0 && rating < 6) {
            return rating;
        }
        throw new ConstraintViolationException("Rating must be in range of 1 to 5", null);
    }

    private String getNameIfValid(String newName) {
        if (!newName.isBlank())
            return newName;
        throw new ConstraintViolationException("Name can not be blank.", null);
    }

}
