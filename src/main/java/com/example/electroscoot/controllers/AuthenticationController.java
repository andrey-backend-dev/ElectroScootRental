package com.example.electroscoot.controllers;

import com.example.electroscoot.dto.AuthenticationDTO;
import com.example.electroscoot.dto.LoginDTO;
import com.example.electroscoot.dto.RegistrationDTO;
import com.example.electroscoot.dto.UserDTO;
import com.example.electroscoot.services.interfaces.IAuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RequiredArgsConstructor
@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    private final IAuthenticationService authenticationService;
    @Value("${jwt.exp.minutes}")
    private Integer jwtExpMinutes;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> register(@RequestBody RegistrationDTO registrationData) {
        AuthenticationDTO authenticationDTO = authenticationService.register(registrationData);
        ResponseCookie jwtCookie = formJwtCookie(authenticationDTO.getJwt());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(authenticationDTO.getUserDTO());
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> login(@RequestBody LoginDTO loginData) {
        AuthenticationDTO authenticationDTO = authenticationService.login(loginData);
        ResponseCookie jwtCookie = formJwtCookie(authenticationDTO.getJwt());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(authenticationDTO.getUserDTO());
    }

    @GetMapping(value = "/logout")
    public ResponseEntity<Object> logout() {
        ResponseCookie jwtCookie = formJwtCookie("");
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .build();
    }

    private ResponseCookie formJwtCookie(String jwt) {
        return ResponseCookie.from("jwt", jwt)
                .maxAge(jwt.isEmpty() ? 0 : jwtExpMinutes * 60L)
                .path("/")
                .secure(true)
                .httpOnly(true)
                .build();
    }
}
