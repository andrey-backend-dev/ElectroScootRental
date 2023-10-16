package com.example.electroscoot.dto;

import com.example.electroscoot.entities.Role;
import com.example.electroscoot.entities.User;
import com.example.electroscoot.utils.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {
    private String username;
    private String phone;
    private String firstname;
    private String secondname;
    private String email;
    private LocalDateTime registeredSince;
    private LocalDateTime subscriptionTill;
    private UserStatus status;
    private float money;
}
