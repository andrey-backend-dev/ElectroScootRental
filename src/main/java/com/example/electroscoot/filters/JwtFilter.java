package com.example.electroscoot.filters;

import com.example.electroscoot.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private Logger logger;
    @Autowired
    private JwtService jwtService;
    @Value("${jwt.url.ignore}")
    private List<String> urlsToIgnore;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = jwtService.getTokenFromRequest(request);

        if (jwt == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!jwtService.validateJWT(jwt)) {
            filterChain.doFilter(request, response);
            return;
        }

        Authentication auth = jwtService.getAuthentication(jwt);
        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return urlsToIgnore.contains(request.getRequestURI());
    }
}
