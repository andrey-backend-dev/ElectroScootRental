package com.example.electroscoot.configs;

import com.example.electroscoot.services.ScooterRentalService;
import com.example.electroscoot.services.interfaces.IScooterRentalService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ScooterRentalServiceTestContextConfiguration {

    @Bean
    public IScooterRentalService scooterRentalService() {
        return new ScooterRentalService();
    }
}
