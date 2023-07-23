package com.example.electroscoot.dto;

import com.example.electroscoot.entities.Role;
import com.example.electroscoot.entities.User;
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
    private float balance;
    private Integer scooterId;

    public UserDTO(User user) {
        this.username = user.getUsername();
        this.phone = user.getPhone();
        this.firstname = user.getFirstname();
        this.secondname = user.getSecondname();
        this.email = user.getEmail();
        this.registeredSince = user.getRegisteredSince();
        this.subscriptionTill = user.getSubscriptionTill();
        this.balance = user.getMoney();
        if (user.getScooter() != null)
            this.scooterId = user.getScooter().getId();
    }
}
