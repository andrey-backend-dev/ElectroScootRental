package com.example.electroscoot.controllers;

import com.example.electroscoot.dto.*;
import com.example.electroscoot.services.interfaces.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final IUserService userService;
    @Value("${jwt.exp.hours}")
    private Integer jwtExpHours;

    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO findById(@PathVariable("id") int id) {
        return userService.findById(id);
    }

    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO findByUsername(@PathVariable("username") String username) {
        return userService.findByUsername(username);
    }

    @GetMapping(value = "/my-account", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO findByPrincipal(Principal principal) {
        return userService.findByUsername(principal.getName());
    }

    @DeleteMapping(value = "/my-account")
    public ResponseEntity<Boolean> deleteByPrincipal(Principal principal) {
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, formCookie("").toString())
                .body(userService.deleteByUsername(principal.getName()));
    }

    @PutMapping(value = "/my-account", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO>  updateByPrincipal(Principal principal, @RequestBody UpdateUserDTO updateData) {
        UpdateUserResponseDTO dto = userService.updateByPrincipal(principal.getName(), updateData);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, formCookie(dto.getJwt()).toString())
                .body(dto.getUserDTO());
    }

    @GetMapping(value = "/my-account/roles", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RoleDTO> getRolesByPrincipal(Principal principal) {
        return userService.findRolesByUsername(principal.getName());
    }

    @PatchMapping(value = "/my-account/add-money", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO addMoneyByPrincipal(Principal principal, @RequestBody MoneyDTO moneyDTO) {
        return userService.addMoneyByUsername(principal.getName(), moneyDTO);
    }

    @GetMapping(value = "/my-account/rent-history", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ScooterRentalDTO> getRentHistoryByPrincipal(Principal principal) {
        return userService.findRentHistoryByUsername(principal.getName());
    }

    @PatchMapping(value = "/my-account/buy-subscription", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO buySubscriptionByPrincipal(Principal principal) {
        return userService.buySubscriptionByUsername(principal.getName());
    }

    private ResponseCookie formCookie(String jwt) {
        return ResponseCookie.from("jwt", jwt)
                .maxAge(jwt.isEmpty() ? 0 : 3600L * jwtExpHours)
                .path("/")
                .secure(true)
                .httpOnly(true)
                .build();
    }
}
