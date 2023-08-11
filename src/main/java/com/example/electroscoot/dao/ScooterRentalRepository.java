package com.example.electroscoot.dao;

import com.example.electroscoot.dto.ScooterRentalDTO;
import com.example.electroscoot.entities.ScooterRental;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScooterRentalRepository extends CrudRepository<ScooterRental, Integer> {

    List<ScooterRental> findByScooterPassedAtIsNotNull();

    List<ScooterRental> findByScooterPassedAtIsNull();
}
