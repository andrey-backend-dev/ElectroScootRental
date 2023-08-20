package com.example.electroscoot.filters;

import com.example.electroscoot.services.JwtService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtFilter implements Filter {
    @Autowired
    private Logger logger;
    @Autowired
    private JwtService jwtService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest formedRequest = (HttpServletRequest) request;
        String jwt = jwtService.getTokenFromRequest(formedRequest);

        if (jwt == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!jwtService.validateJWT(jwt)) {
//            mb some exception
            filterChain.doFilter(request, response);
            return;
        }

        Authentication auth = jwtService.getAuthentication(jwt);
        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
