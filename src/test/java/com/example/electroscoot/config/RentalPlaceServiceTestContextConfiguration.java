package com.example.electroscoot.config;

import com.example.electroscoot.services.RentalPlaceService;
import com.example.electroscoot.services.interfaces.IRentalPlaceService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class RentalPlaceServiceTestContextConfiguration {

    @Bean
    public IRentalPlaceService rentalPlaceService() {
        return new RentalPlaceService();
    }
}
