package com.example.electroscoot.controllers;

import com.example.electroscoot.dto.*;
import com.example.electroscoot.services.interfaces.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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

    @PostMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean logout(HttpServletRequest request) {
        logger.info("The <logout> method is called from User Controller.");
        return userService.logout(request.getHeader("Authorization"));
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

    @GetMapping(value = "/my-account", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO findByPrincipal(Principal principal) {
        logger.info("The <findByPrincipal> method is called from User Controller.");
        return userService.findByUsername(principal.getName());
    }

    @DeleteMapping(value = "/my-account")
    public boolean deleteByToken(HttpServletRequest request) {
        logger.info("The <deleteByPrincipal> method is called from User Controller.");
        return userService.deleteByToken(request.getHeader("Authorization"));
    }

    @PutMapping(value = "/my-account", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UpdateUserResponseDTO updateByToken(HttpServletRequest request, @RequestBody UpdateUserDTO updateData) {
        logger.info("The <updateByToken> method is called from User Controller.");
        return userService.updateByToken(request.getHeader("Authorization"), updateData);
    }

    @GetMapping(value = "/my-account/roles", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RoleDTO> getRolesByPrincipal(Principal principal) {
        logger.info("The <getRolesByPrincipal> method is called from User Controller.");
        return userService.getRolesByUsername(principal.getName());
    }

    @PatchMapping(value = "/my-account/add-money", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO addMoneyByPrincipal(Principal principal, @RequestBody MoneyDTO moneyDTO) {
        logger.info("The <addMoneyByPrincipal> method is called from User Controller.");
        return userService.addMoneyByUsername(principal.getName(), moneyDTO);
    }

    @GetMapping(value = "/my-account/rent-history", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ScooterRentalDTO> getRentHistoryByPrincipal(Principal principal) {
        logger.info("The <getRentHistoryByPrincipal> method is called from User Controller.");
        return userService.getRentHistoryByUsername(principal.getName());
    }

    @PatchMapping(value = "/my-account/buy-subscription", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO buySubscriptionByPrincipal(Principal principal) {
        logger.info("The <buySubscriptionByPrincipal> method is called from User Controller.");
        return userService.buySubscriptionByUsername(principal.getName());
    }
}
