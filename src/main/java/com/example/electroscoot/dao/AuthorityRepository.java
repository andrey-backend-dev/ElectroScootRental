package com.example.electroscoot.dao;

import com.example.electroscoot.entities.Authority;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends CrudRepository<Authority, String> {
    Optional<Authority> findByName(String name);
}
