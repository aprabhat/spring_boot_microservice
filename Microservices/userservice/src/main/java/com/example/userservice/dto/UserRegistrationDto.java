package com.example.userservice.dto;

import lombok.Data;

@Data
public class UserRegistrationDto {

    private String username;
    private String email;
    private String password;

    // getters and setters
}
