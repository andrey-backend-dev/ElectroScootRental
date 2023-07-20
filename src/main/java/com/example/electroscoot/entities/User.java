package com.example.electroscoot.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private String phone;
    private LocalDateTime registeredSince;
    private String firstname;
    private String secondname;
    private String email;
    private LocalDateTime subscriptionTill;
    private float money;
    @ManyToMany(mappedBy = "users")
    private Set<Role> roles;
    @OneToMany(mappedBy = "user")
    private Set<ScooterRental> scooterRentals;
}
