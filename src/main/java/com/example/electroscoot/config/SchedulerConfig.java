package com.example.electroscoot.config;

import com.example.electroscoot.infra.TriggerRentalSchedulerClock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@Configuration
public class SchedulerConfig {

    @Bean(name = "TriggerRentalSchedulerClock")
    @Scope("prototype")
    public TriggerRentalSchedulerClock triggerRentalSchedulerClock(int scooterRentalId) {
        return new TriggerRentalSchedulerClock(scooterRentalId);
    }
}
