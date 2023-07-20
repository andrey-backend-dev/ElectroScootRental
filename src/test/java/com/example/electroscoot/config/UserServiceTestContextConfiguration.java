package com.example.electroscoot.config;

import com.example.electroscoot.services.ScooterService;
import com.example.electroscoot.services.UserService;
import com.example.electroscoot.services.interfaces.IScooterService;
import com.example.electroscoot.services.interfaces.IUserService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class UserServiceTestContextConfiguration {

    @Bean
    public IUserService userService() {
        return new UserService();
    }
}
