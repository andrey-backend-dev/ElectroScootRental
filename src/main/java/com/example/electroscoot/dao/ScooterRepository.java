package com.example.electroscoot.dao;

import com.example.electroscoot.entities.Scooter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScooterRepository extends CrudRepository<Scooter, Integer> {
}
