package com.dropdown.dto;

public record AuthResponse(
        String token,
        String refreshToken
) {
}
