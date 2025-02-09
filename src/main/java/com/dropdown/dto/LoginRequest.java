package com.dropdown.dto;

public record LoginRequest(
        String email,
        String password,
        String role,
        double latitude,
        double longitude
) {
}
