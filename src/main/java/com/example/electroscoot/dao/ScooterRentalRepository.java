package com.example.electroscoot.dao;

import com.example.electroscoot.entities.ScooterRental;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScooterRentalRepository extends CrudRepository<ScooterRental, Integer> {
}
