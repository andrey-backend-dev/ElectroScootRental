package com.example.electroscoot.entities;

import com.example.electroscoot.utils.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@AllArgsConstructor
@Getter
@Setter
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
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    private float money;
    @OneToOne
    @JoinColumn(name = "scooter_id", referencedColumnName = "id")
    private Scooter scooter;
    @ManyToMany
    @JoinTable(
            name = "user2role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;
    @OneToMany(mappedBy = "user")
    private Set<ScooterRental> scooterRentals;
}
