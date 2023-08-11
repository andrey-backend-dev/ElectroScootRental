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

    public static void main(String[] args) throws AccessDeniedException {
        ApplicationContext context = SpringApplication.run(ElectroScootApplication.class, args);
    }
}
