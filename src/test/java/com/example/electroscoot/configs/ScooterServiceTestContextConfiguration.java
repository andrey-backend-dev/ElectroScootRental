package com.example.electroscoot.configs;

import com.example.electroscoot.services.ScooterService;
import com.example.electroscoot.services.interfaces.IScooterService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ScooterServiceTestContextConfiguration {

    @Bean
    public IScooterService scooterService() {
        return new ScooterService();
    }
}
