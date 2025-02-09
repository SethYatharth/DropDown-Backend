package com.dropdown.dto;

public record RegistrationRequest(
        String name,
        String email,
        String password,
        String phoneNo,
        String role,
        double latitude,
        double longitude

) {
}
