package com.example.electroscoot.dao;

import com.example.electroscoot.entities.ScooterModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScooterModelRepository extends CrudRepository<ScooterModel, Integer> {
    ScooterModel findByName(String model);

    boolean deleteByName(String name);
}
