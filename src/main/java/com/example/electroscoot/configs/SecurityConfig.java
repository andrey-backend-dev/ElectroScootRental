package com.example.electroscoot.configs;

import com.example.electroscoot.exceptions.handlers.DelegatingToControllerAdviceAccessDeniedHandler;
import com.example.electroscoot.filters.JwtExceptionHandlerFilter;
import com.example.electroscoot.filters.JwtFilter;
import com.example.electroscoot.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.session.DisableEncodeUrlFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private CustomUserDetailsService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests.
                        requestMatchers("/users/register", "/users/login").permitAll().
                        requestMatchers(HttpMethod.GET,
                                "/users/", "/users/{username}/roles", "/users/{username}/rent-history",
                                "/scooter-models/", "/scooter-models/scooters", "/rentals/").hasRole("ADMIN").
                        requestMatchers(HttpMethod.POST,
                                "/scooter-models/create", "/rental-places/create", "/scooters/create").hasRole("ADMIN").
                        requestMatchers(HttpMethod.PUT,
                                "/scooter-models/{name}", "/rental-places/{name}", "/scooters/{id}").hasRole("ADMIN").
                        requestMatchers(HttpMethod.DELETE,
                                "/scooter-models/{name}", "/rental-places/{name}", "/scooters/{id}").hasRole("ADMIN").
                        requestMatchers(HttpMethod.PATCH,
                                "/users/{username}/roles/add", "/users/{username}/roles/remove",
                                "/users/{username}/change-status").hasRole("ADMIN").
                        anyRequest().authenticated())
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionHandlerFilter(), JwtFilter.class)
                .exceptionHandling(handler -> {
                    handler.accessDeniedHandler(delegatingAccessDeniedHandler());
                });

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userService);
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter();
    }

    @Bean
    public JwtExceptionHandlerFilter jwtExceptionHandlerFilter() {
        return new JwtExceptionHandlerFilter();
    }

    @Bean
    public AccessDeniedHandler delegatingAccessDeniedHandler() {
        return new DelegatingToControllerAdviceAccessDeniedHandler();
    }
}
