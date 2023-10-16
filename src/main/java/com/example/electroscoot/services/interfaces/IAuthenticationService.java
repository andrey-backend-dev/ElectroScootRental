package com.example.electroscoot.services.interfaces;

import com.example.electroscoot.dto.AuthenticationDTO;
import com.example.electroscoot.dto.LoginDTO;
import com.example.electroscoot.dto.RegistrationDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public interface IAuthenticationService {
    AuthenticationDTO register(@Valid RegistrationDTO registrationData);
    AuthenticationDTO login(@Valid LoginDTO loginData);
    boolean logout(@NotBlank(message = "Bearer token is mandatory.") String bearer);
}
