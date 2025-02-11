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
        String role,

        @DecimalMin(value = "-90.0", message = "Latitude must be between -90 and 90")
        @DecimalMax(value = "90.0", message = "Latitude must be between -90 and 90")
        double latitude,

        @DecimalMin(value = "-180.0", message = "Longitude must be between -180 and 180")
        @DecimalMax(value = "180.0", message = "Longitude must be between -180 and 180")
        double longitude
) {
}

//package com.dropdown.dto;
//
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Size;
//
//public record RegistrationRequest(
//        @NotNull
//        String name,
//        @NotNull
//        @Email
//        String email,
//        @NotNull
//        @Size(min = 8)
//        String password,
//        @NotNull
//        String phoneNo,
//        @NotNull
//        String role,
//        @NotNull
//        double latitude,
//        @NotNull
//        double longitude
//
//) {
//}
