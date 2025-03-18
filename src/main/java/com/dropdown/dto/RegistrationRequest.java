package com.dropdown.dto;

import jakarta.validation.constraints.*;

public record RegistrationRequest(
        @NotBlank(message = "Name cannot be blank")
        String name,

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Password cannot be blank")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        String password,

        @NotBlank(message = "Phone number cannot be blank")
        @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Invalid phone number format")
        String phoneNo,

        @NotBlank(message = "Role cannot be blank")
        String role
) {
}
