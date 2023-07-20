package com.example.electroscoot;

import com.example.electroscoot.dto.CreateRentalPlaceDTO;
import com.example.electroscoot.dto.CreateScooterDTO;
import com.example.electroscoot.dto.CreateScooterModelDTO;
import com.example.electroscoot.dto.RegistrationDTO;
import com.example.electroscoot.dto.ScooterDTO;
import com.example.electroscoot.dto.ScooterModelDTO;
import com.example.electroscoot.dto.UpdateRentalPlaceDTO;
import com.example.electroscoot.dto.UpdateScooterDTO;
import com.example.electroscoot.dto.UpdateScooterModelDTO;
import com.example.electroscoot.dto.UpdateUserDTO;
import com.example.electroscoot.entities.RentalPlace;
import com.example.electroscoot.entities.Scooter;
import com.example.electroscoot.entities.ScooterModel;
import com.example.electroscoot.entities.User;
import com.example.electroscoot.services.RentalPlaceService;
import com.example.electroscoot.services.RoleService;
import com.example.electroscoot.services.ScooterModelService;
import com.example.electroscoot.services.ScooterService;
import com.example.electroscoot.services.UserService;
import com.example.electroscoot.services.interfaces.IRentalPlaceService;
import com.example.electroscoot.services.interfaces.IRoleService;
import com.example.electroscoot.services.interfaces.IScooterModelService;
import com.example.electroscoot.services.interfaces.IScooterService;
import com.example.electroscoot.services.interfaces.IUserService;
import com.example.electroscoot.utils.enums.StateEnum;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ElectroScootApplication {
    private static IUserService userService;
    private static IRoleService roleService;
    private static IScooterModelService scooterModelService;
    private static IScooterService scooterService;
    private static IRentalPlaceService rentalPlaceService;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ElectroScootApplication.class, args);
        userService = context.getBean(IUserService.class);
        roleService = context.getBean(IRoleService.class);
        scooterModelService = context.getBean(IScooterModelService.class);
        scooterService = context.getBean(IScooterService.class);
        rentalPlaceService = context.getBean(IRentalPlaceService.class);

//        roleService.create("USER");
//
//        RegistrationDTO registrationDTO = new RegistrationDTO("bladeattheneck", "12356", "8053535");
//        userService.register(registrationDTO);
//
//        ScooterModelDTO scooterModelDTO = scooterModelService.create(new CreateScooterModelDTO("fx2301", 100.0F, 300.0F));
//
//        rentalPlaceService.create(new CreateRentalPlaceDTO("brthome", "Moscow", "brt", 10));

        System.out.println(scooterModelService.getScootersByName("fx2301"));

        ScooterDTO scooterDTO = scooterService.create(new CreateScooterDTO("brthome", "fx2301", StateEnum.OK));

        System.out.println(scooterModelService.getScootersByName("fx2301"));

        scooterService.deleteById(scooterDTO.getId());

        System.out.println(scooterModelService.getScootersByName("fx2301"));

    }
}
