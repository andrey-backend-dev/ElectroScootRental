package com.example.electroscoot.dao;

import com.example.electroscoot.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);

    void deleteByUsername(String username);
}
