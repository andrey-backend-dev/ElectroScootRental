package com.example.electroscoot.dao;

import com.example.electroscoot.entities.ScooterModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScooterModelRepository extends CrudRepository<ScooterModel, Integer> {
    Optional<ScooterModel> findByName(String model);

    void deleteByName(String name);
}
