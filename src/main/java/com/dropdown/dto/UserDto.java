package com.dropdown.dto;

import lombok.Builder;

@Builder
public record UserDto(String email, String name, String phoneNo) {
}
