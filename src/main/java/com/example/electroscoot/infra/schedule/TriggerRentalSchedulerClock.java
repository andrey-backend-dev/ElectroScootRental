package com.example.electroscoot.infra.schedule;

import com.example.electroscoot.services.interfaces.IScooterRentalService;
import com.example.electroscoot.utils.enums.RentalStateEnum;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;

import java.time.Clock;
import java.time.LocalDateTime;

public class TriggerRentalSchedulerClock {
    @Autowired
    private IScooterRentalService scooterRentalService;
    @Autowired
    private Clock clock;
    @Autowired
    private ScheduledAnnotationBeanPostProcessor scheduledAnnotationBeanPostProcessor;
    @Autowired
    private Logger logger;
    @Value("${business.pricePerTimeInSeconds}")
    private String pricePerTimeInSeconds;

    private int scooterRentalId;

    public TriggerRentalSchedulerClock(int scooterRentalId) {
        this.scooterRentalId = scooterRentalId;
    }

    @Scheduled(initialDelayString = "${business.pricePerTimeInSeconds}000",fixedDelayString = "${business.pricePerTimeInSeconds}000")
    public void scheduledTask() {
        logger.debug("Scheduler for scooter rental with id " +
                scooterRentalId + " is called at " + LocalDateTime.now(clock));

        RentalStateEnum rentalState = scooterRentalService.takePaymentById(scooterRentalId);

        if (rentalState == RentalStateEnum.BAD || rentalState == RentalStateEnum.CLOSED) {
            logger.debug("Scheduler for scooter rental with id " + scooterRentalId + " is stopped. Reason: " + rentalState.getName());
            scheduledAnnotationBeanPostProcessor.postProcessBeforeDestruction(this, "TriggerRentalSchedulerClock");
        }
    }
}
