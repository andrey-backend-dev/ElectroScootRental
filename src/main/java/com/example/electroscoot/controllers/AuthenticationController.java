package com.example.electroscoot.controllers;

import com.example.electroscoot.dto.AuthenticationDTO;
import com.example.electroscoot.dto.LoginDTO;
import com.example.electroscoot.dto.RegistrationDTO;
import com.example.electroscoot.services.interfaces.IAuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    private final IAuthenticationService authenticationService;
    private final Logger logger;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthenticationDTO register(@RequestBody RegistrationDTO registrationData) {
        logger.info("The <register> method is called from Authentication Controller.");
        return authenticationService.register(registrationData);
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthenticationDTO login(@RequestBody LoginDTO loginData) {
        logger.info("The <login> method is called from Authentication Controller.");
        return authenticationService.login(loginData);
    }

    @GetMapping(value = "/logout")
    public boolean logout(HttpServletRequest request) {
        logger.info("The <logout> method is called from Authentication Controller.");
        return authenticationService.logout(request.getHeader("Authorization"));
    }
}
