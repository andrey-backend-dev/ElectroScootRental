package com.example.electroscoot;

import com.example.electroscoot.dto.CreateScooterRentalDTO;
import com.example.electroscoot.dto.ScooterRentalDTO;
import com.example.electroscoot.services.interfaces.IRentalPlaceService;
import com.example.electroscoot.services.interfaces.IRoleService;
import com.example.electroscoot.services.interfaces.IScooterModelService;
import com.example.electroscoot.services.interfaces.IScooterRentalService;
import com.example.electroscoot.services.interfaces.IScooterService;
import com.example.electroscoot.services.interfaces.IUserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

@SpringBootApplication
public class ElectroScootApplication {
    private static IUserService userService;
    private static IRoleService roleService;
    private static IScooterModelService scooterModelService;
    private static IScooterService scooterService;
    private static IRentalPlaceService rentalPlaceService;
    private static IScooterRentalService scooterRentalService;

    public static void main(String[] args) throws AccessDeniedException {
        ApplicationContext context = SpringApplication.run(ElectroScootApplication.class, args);
        userService = context.getBean(IUserService.class);
        roleService = context.getBean(IRoleService.class);
        scooterModelService = context.getBean(IScooterModelService.class);
        scooterService = context.getBean(IScooterService.class);
        rentalPlaceService = context.getBean(IRentalPlaceService.class);
        scooterRentalService = context.getBean(IScooterRentalService.class);

//        CreateScooterRentalDTO createScooterRentalDTO = new CreateScooterRentalDTO("bladeattheneck", 1);
//
//        System.out.println(userService.findByUsername("bladeattheneck"));
//        ScooterRentalDTO scooterRentalDTO = scooterRentalService.create(createScooterRentalDTO);
//        System.out.println(scooterRentalDTO);
//        System.out.println(userService.findByUsername("bladeattheneck"));
//        System.out.println("sleep 5 seconds");
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(userService.findByUsername("bladeattheneck"));
//
//        System.out.println(scooterRentalService.closeRentalById(scooterRentalDTO.getId(), scooterRentalDTO.getInitRentalPlaceName()));
//
//        System.out.println(userService.findByUsername("bladeattheneck"));

//        CreateScooterRentalDTO createScooterRentalDTO = new CreateScooterRentalDTO("bladeattheneck", 1);
//        ScooterRentalDTO scooterRentalDTO = scooterRentalService.create(createScooterRentalDTO);
//
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        CreateScooterRentalDTO createScooterRentalDTO2 = new CreateScooterRentalDTO("testUser", 3);
        ScooterRentalDTO scooterRentalDTO2 = scooterRentalService.create(createScooterRentalDTO2);

        LocalDateTime finishAt = LocalDateTime.now().plusSeconds(10);
        boolean go = true;

        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (LocalDateTime.now().isAfter(finishAt) && go) {
                System.out.println(scooterRentalService.closeRentalById(scooterRentalDTO2.getId(), "ТРЦ СПБ"));
                go = !go;
            }
        }

    }
}
