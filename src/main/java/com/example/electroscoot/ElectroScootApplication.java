package com.example.electroscoot;

import com.example.electroscoot.controllers.RentalPlaceController;
import com.example.electroscoot.controllers.RoleController;
import com.example.electroscoot.controllers.ScooterController;
import com.example.electroscoot.controllers.ScooterModelController;
import com.example.electroscoot.controllers.ScooterRentalController;
import com.example.electroscoot.controllers.UserController;
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
    private static UserController userController;
    private static RoleController roleController;
    private static ScooterModelController scooterModelController;
    private static ScooterController scooterController;
    private static RentalPlaceController rentalPlaceController;
    private static ScooterRentalController scooterRentalController;

    public static void main(String[] args) throws AccessDeniedException {
        ApplicationContext context = SpringApplication.run(ElectroScootApplication.class, args);
        userController = context.getBean(UserController.class);
        roleController = context.getBean(RoleController.class);
        scooterModelController = context.getBean(ScooterModelController.class);
        scooterController = context.getBean(ScooterController.class);
        rentalPlaceController = context.getBean(RentalPlaceController.class);
        scooterRentalController = context.getBean(ScooterRentalController.class);

//        CreateScooterRentalDTO createScooterRentalDTO = new CreateScooterRentalDTO("bladeattheneck", 1);
//        ScooterRentalDTO scooterRentalDTO = scooterRentalService.create(createScooterRentalDTO);
//
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        CreateScooterRentalDTO createScooterRentalDTO2 = new CreateScooterRentalDTO("testUser", 3);
//        ScooterRentalDTO scooterRentalDTO2 = scooterRentalService.create(createScooterRentalDTO2);
//
//        LocalDateTime finishAt = LocalDateTime.now().plusSeconds(10);
//        boolean go = true;
//
//        while (true) {
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            if (LocalDateTime.now().isAfter(finishAt) && go) {
//                System.out.println(scooterRentalService.closeRentalById(scooterRentalDTO2.getId(), "ТРЦ СПБ"));
//                go = !go;
//            }
//        }
    }
}
