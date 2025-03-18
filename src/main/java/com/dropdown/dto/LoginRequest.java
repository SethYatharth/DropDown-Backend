package com.dropdown.dto;

import jakarta.validation.constraints.*;

public record LoginRequest(

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Password cannot be blank")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        String password,


        @NotBlank(message = "Role cannot be blank")
        String role
) {
}
