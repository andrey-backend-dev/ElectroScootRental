package com.example.electroscoot.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Role {
    @Id
    private String name;
    @ManyToMany
    @JoinTable(name = "role2authority",
    joinColumns = @JoinColumn(name = "role_name"),
    inverseJoinColumns = @JoinColumn(name = "authority_name"))
    private Set<Authority> authorities;
}
