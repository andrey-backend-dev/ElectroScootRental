package com.example.electroscoot.config;

import com.example.electroscoot.services.ScooterModelService;
import com.example.electroscoot.services.interfaces.IScooterModelService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ScooterModelServiceTestContextConfiguration {

    @Bean
    public IScooterModelService scooterModelService() {
        return new ScooterModelService();
    }
}
