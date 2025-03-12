package com.dropdown.dto;

public record AuthResponse(
        String id,
        String token,
        String refreshToken
) {
}
