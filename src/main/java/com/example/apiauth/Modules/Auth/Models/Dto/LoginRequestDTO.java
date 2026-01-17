package com.example.apiauth.Modules.Auth.Models.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {

    @NotBlank(message = "The name is mandatory")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "The name is mandatory")
    private String password;
}