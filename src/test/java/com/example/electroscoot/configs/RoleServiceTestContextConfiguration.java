package com.example.electroscoot.configs;

import com.example.electroscoot.services.RoleService;
import com.example.electroscoot.services.interfaces.IRoleService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class RoleServiceTestContextConfiguration {

    @Bean
    public IRoleService roleService() {
        return new RoleService();
    }
}
