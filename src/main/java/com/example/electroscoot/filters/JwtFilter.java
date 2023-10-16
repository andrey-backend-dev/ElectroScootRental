package com.example.electroscoot.filters;

import com.example.electroscoot.services.interfaces.IJwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final IJwtService jwtService;
    @Value("${jwt.url.ignore}")
    private List<String> urlsToIgnore;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = jwtService.getTokenFromRequest(request);

        if (jwt == null) {
            filterChain.doFilter(request, response);
            return;
        }

        boolean jwtValidationResult;
        try {
            jwtValidationResult = jwtService.validateJWT(jwt);
        } catch (JwtException ex) {
//            если в headers лежит невалидный jwt, а мы пытаемся зайти на страницы, для которых jwt не требуется
            if (urlsToIgnore.contains(request.getRequestURI())) {
                filterChain.doFilter(request, response);
                return;
            }
//            иначе в обычном режиме кидаем эксепшн
            throw ex;
        }

        if (jwtValidationResult) {
//            если мы пытаемся зайти на страницы, на которые нельзя зайти с валидным jwt токеном (регистрация/login)
            if (urlsToIgnore.contains(request.getRequestURI())) {
                throw new AccessDeniedException("You can not visit this page, till you are authenticated.");
            }
        } else {
            filterChain.doFilter(request, response);
            return;
        }

        Authentication auth = jwtService.getAuthentication(jwt);
        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);
    }
}
