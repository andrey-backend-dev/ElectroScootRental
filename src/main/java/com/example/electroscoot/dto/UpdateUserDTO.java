package com.example.electroscoot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateUserDTO {
    private String username;
    private String phone;
    private String firstname;
    private String secondname;
    private String email;
}
