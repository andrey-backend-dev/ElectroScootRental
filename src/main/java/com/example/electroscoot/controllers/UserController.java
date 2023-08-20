package com.example.electroscoot.controllers;

import com.example.electroscoot.dto.*;
import com.example.electroscoot.services.interfaces.IUserService;
import com.example.electroscoot.utils.enums.UserStatus;
import com.example.electroscoot.utils.maps.UserStatusMap;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private Logger logger;

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public AuthenticationDTO register(@RequestBody RegistrationDTO registrationData) {
        logger.info("The <register> method is called from User Controller.");
        return userService.register(registrationData);
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public AuthenticationDTO login(@RequestBody LoginDTO loginDTO) {
        logger.info("The <login> method is called from User Controller.");
        return userService.login(loginDTO);
    }

    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO findById(@PathVariable("id") int id) {
        logger.info("The <findById> method is called from User Controller.");
        return userService.findById(id);
    }

    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO findByUsername(@PathVariable("username") String username) {
        logger.info("The <findByUsername> method is called from User Controller.");
        return userService.findByUsername(username);
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDTO> getList() {
        logger.info("The <getList> method is called from User Controller.");
        return userService.getList();
    }

    @DeleteMapping(value = "/{username}")
    public boolean deleteByUsername(@PathVariable("username") String username) {
        logger.info("The <deleteByUsername> method is called from User Controller.");
        return userService.deleteByUsername(username);
    }

    @PutMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO updateByUsername(@PathVariable("username") String username, @RequestBody UpdateUserDTO updateData) {
        logger.info("The <updateByUsername> method is called from User Controller.");
        return userService.updateByUsername(username, updateData);
    }

    @GetMapping(value = "/{username}/roles", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RoleDTO> getRolesByUsername(@PathVariable("username") String username) {
        logger.info("The <getRolesByUsername> method is called from User Controller.");
        return userService.getRolesByUsername(username);
    }

    @PatchMapping(value = "/{username}/roles/add", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<RoleDTO> addRoleByUsername(@PathVariable("username") String username, @RequestBody RoleNameDTO roleNameDTO) {
        logger.info("The <addRoleByUsername> method is called from User Controller.");
        return userService.addRoleByUsername(username, roleNameDTO);
    }

    @PatchMapping(value = "/{username}/roles/remove", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<RoleDTO> removeRoleByUsername(@PathVariable("username") String username, @RequestBody RoleNameDTO roleNameDTO) {
        logger.info("The <removeRoleByUsername> method is called from User Controller.");
        return userService.removeRoleByUsername(username, roleNameDTO);
    }

    @PatchMapping(value = "/{username}/add-money", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO addMoneyByUsername(@PathVariable("username") String username, @RequestBody MoneyDTO moneyDTO) {
        logger.info("The <addMoneyByUsername> method is called from User Controller.");
        return userService.addMoneyByUsername(username, moneyDTO);
    }

    @GetMapping(value = "/{username}/rent-history", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ScooterRentalDTO> getRentHistoryByUsername(@PathVariable("username") String username) {
        logger.info("The <getRentHistoryByUsername> method is called from User Controller.");
        return userService.getRentHistoryByUsername(username);
    }

    @PatchMapping(value = "/{username}/buy-subscription", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO buySubscriptionByUsername(@PathVariable("username") String username) {
        logger.info("The <buySubscriptionByUsername> method is called from User Controller.");
        return userService.buySubscriptionByUsername(username);
    }

    @PatchMapping(value = "/{username}/change-status", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO changeUserStatusByUsername(@PathVariable("username") String username,
                                              @RequestParam("status") String status) {
        logger.info("The <changeUserStatusByUsername> method is called from User Controller.");
        UserStatus userStatus = UserStatusMap.getStatusByName(status);
        return userService.changeUserStatusByUsername(username, userStatus);
    }
}
