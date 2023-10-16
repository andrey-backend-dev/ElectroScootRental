package com.example.electroscoot.services.interfaces;

import com.example.electroscoot.entities.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

import java.net.http.HttpRequest;

public interface IJwtService {
    String getTokenFromRequest(HttpServletRequest request);
    String generateJWT(User user);
    boolean validateJWT(String jwt);
    Authentication getAuthentication(String jwt);
    String extractUsername(String jwt);
}
