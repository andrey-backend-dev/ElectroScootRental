package com.example.electroscoot.dao;

import com.example.electroscoot.entities.RentalPlace;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalPlaceRepository extends CrudRepository<RentalPlace, Integer> {
    RentalPlace findByName(String name);

    void deleteByName(String name);
}
