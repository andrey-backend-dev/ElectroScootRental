package com.example.electroscoot.dao;

import com.example.electroscoot.dto.ScooterDTO;
import com.example.electroscoot.entities.Scooter;
import com.example.electroscoot.utils.enums.ScooterStateEnum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ScooterRepository extends CrudRepository<Scooter, Integer> {

    List<Scooter> findByOrderByStateAsc();

    List<Scooter> findByOrderByStateDesc();

    List<Scooter> findByState(ScooterStateEnum state);
}
