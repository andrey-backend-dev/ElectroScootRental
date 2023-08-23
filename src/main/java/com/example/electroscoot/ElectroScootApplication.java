package com.example.electroscoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.nio.file.AccessDeniedException;

@SpringBootApplication
public class ElectroScootApplication {

    public static void main(String[] args) throws AccessDeniedException {
        ApplicationContext context = SpringApplication.run(ElectroScootApplication.class, args);
    }
}
