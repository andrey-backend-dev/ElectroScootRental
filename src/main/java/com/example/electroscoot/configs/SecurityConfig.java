package com.example.electroscoot.configs;

import com.example.electroscoot.exceptions.handlers.DelegatingToControllerAdviceAccessDeniedHandler;
import com.example.electroscoot.filters.JwtExceptionHandlerFilter;
import com.example.electroscoot.filters.JwtFilter;
import com.example.electroscoot.services.CustomUserDetailsService;
import com.example.electroscoot.services.interfaces.IJwtService;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomUserDetailsService userService;
    private final IJwtService jwtService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests
                                .requestMatchers("/authentication/register", "/authentication/login").permitAll()
                                .requestMatchers(HttpMethod.POST, "/users/create").hasAuthority("user_control")
                                .requestMatchers(HttpMethod.GET, "/users/", "/users/{username}/roles", "/users/{username}/rent-history").hasAuthority("user_control")
                                .requestMatchers(HttpMethod.PATCH, "/users/{username}/roles/add", "/users/{username}/roles/remove",
                                        "/users/{username}/add-money", "/users/{username}/change-status").hasAuthority("user_control")
                                .requestMatchers(HttpMethod.POST, "/roles/create").hasAuthority("role_create")
                                .requestMatchers(HttpMethod.GET, "/roles/", "/roles/{name}").hasAuthority("role_read")
                                .requestMatchers(HttpMethod.DELETE, "/roles/{name}").hasAuthority("role_delete")
                                .requestMatchers(HttpMethod.PATCH, "/roles/{name}/add-authorities", "/roles/{name}/remove-authorities").hasAuthority("role_update") // add methods!
                                .requestMatchers(HttpMethod.POST, "/scooters/create", "/scooter-models/create").hasAuthority("scooter_create")
                                .requestMatchers(HttpMethod.GET, "/scooters/", "/scooter-models/", "/scooter-models/{name}/scooters").hasAuthority("scooter_read")
                                .requestMatchers(HttpMethod.DELETE, "/scooters/{id}", "/scooter-models/{name}").hasAuthority("scooter_delete")
                                .requestMatchers(HttpMethod.PUT, "/scooters/{id}", "/scooter-models/{name}").hasAuthority("scooter_update")
                                .requestMatchers(HttpMethod.POST, "/rental-places/create").hasAuthority("rental_place_create")
                                .requestMatchers(HttpMethod.DELETE, "/rental-places/{name}").hasAuthority("rental_place_delete")
                                .requestMatchers(HttpMethod.PUT, "/rental-places/{name}").hasAuthority("rental_place_update")
                                .requestMatchers(HttpMethod.GET, "/rentals/", "/rentals/{id}", "/users/{username}/rent-history").hasAuthority("scooter_rental_read")
                                .requestMatchers(HttpMethod.PATCH, "/rentals/{id}/close").hasAuthority("scooter_rental_close")
                                .anyRequest().authenticated())
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
        return new JwtFilter(jwtService);
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
