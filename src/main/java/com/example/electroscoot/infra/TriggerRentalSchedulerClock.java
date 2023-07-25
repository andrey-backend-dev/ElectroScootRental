package com.example.electroscoot.infra;

import com.example.electroscoot.services.interfaces.IScooterRentalService;
import com.example.electroscoot.utils.enums.RentalStateEnum;
import org.springframework.beans.factory.annotation.Autowired;
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

    private int scooterRentalId;

    public TriggerRentalSchedulerClock(int scooterRentalId) {
        this.scooterRentalId = scooterRentalId;
    }

    @Scheduled(initialDelayString = "${business.initDelayBeforeFirstPayInSeconds}000",fixedDelayString = "${business.pricePerTimeInSeconds}000")
    public void scheduledTask() {
        System.out.println("Scheduler for scooter rental with id " +
                scooterRentalId + " is called at " + LocalDateTime.now(clock));

        RentalStateEnum rentalState = scooterRentalService.takePaymentById(scooterRentalId);

        if (rentalState == RentalStateEnum.BAD) {
            System.out.println("Scheduler for scooter rental with id " + scooterRentalId + " is stopped, cause user does not have enough money to continue the rent.");
            scheduledAnnotationBeanPostProcessor.postProcessBeforeDestruction(this, "TriggerRentalSchedulerClock");
        } else if (rentalState == RentalStateEnum.CLOSED) {
            System.out.println("Scheduler for scooter rental with id " + scooterRentalId + " is stopped, cause rent is already closed.");
            scheduledAnnotationBeanPostProcessor.postProcessBeforeDestruction(this, "TriggerRentalSchedulerClock");
        }
    }
}
