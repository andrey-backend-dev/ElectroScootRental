package com.example.electroscoot.dao;

import com.example.electroscoot.dto.RentalPlaceDTO;
import com.example.electroscoot.entities.RentalPlace;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalPlaceRepository extends CrudRepository<RentalPlace, Integer> {
    RentalPlace findByName(String name);

    void deleteByName(String name);

    List<RentalPlace> findAll(Sort sort);

    List<RentalPlace> findByOrderByCityAscStreetAscHouseAsc();

    List<RentalPlace> findByCity(String city);
}
