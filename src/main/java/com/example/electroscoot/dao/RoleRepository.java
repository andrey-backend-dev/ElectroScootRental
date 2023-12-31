package com.example.electroscoot.dao;

import com.example.electroscoot.entities.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, String> {
    Optional<Role> findByName(String name);

    void deleteByName(String name);
}
