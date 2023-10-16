package com.example.electroscoot.services;

import com.example.electroscoot.dao.UserRepository;
import com.example.electroscoot.dto.AuthenticationDTO;
import com.example.electroscoot.dto.LoginDTO;
import com.example.electroscoot.dto.RegistrationDTO;
import com.example.electroscoot.entities.User;
import com.example.electroscoot.infra.schedule.JwtBlacklistScheduler;
import com.example.electroscoot.services.interfaces.IAuthenticationService;
import com.example.electroscoot.services.interfaces.IUserService;
import com.example.electroscoot.utils.mappers.UserMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService implements IAuthenticationService {
    private final UserRepository userRepository;
    private final IUserService userService;
    private final JwtService jwtService;
    private final UserMapper mapper;
    private final AuthenticationManager authenticationManager;
    private final JwtBlacklistScheduler jwtBlacklistScheduler;

    @Override
    public AuthenticationDTO register(@Valid RegistrationDTO registrationData) {
        User user = userService.create(registrationData);
        String jwt = jwtService.generateJWT(user);
        return new AuthenticationDTO(mapper.userToUserDto(user), jwt);
    }

    @Override
    public AuthenticationDTO login(@Valid LoginDTO loginData) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginData.getUsername(), loginData.getPassword()));

        User user = userRepository.findByUsername(loginData.getUsername()).orElseThrow(() -> {
            return new IllegalArgumentException("The user with username " + loginData.getUsername() + " does not exist.");
        });

        String jwt = jwtService.generateJWT(user);

        return new AuthenticationDTO(mapper.userToUserDto(user), jwt);
    }

    @Override
    public boolean logout(@NotBlank(message = "Bearer token is mandatory.") String bearer) {
        String jwt = bearer.substring(7);

        return jwtBlacklistScheduler.blacklistJwt(jwt);
    }
}
