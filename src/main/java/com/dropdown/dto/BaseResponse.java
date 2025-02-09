package com.dropdown.dto;

import lombok.Builder;

@Builder
public record BaseResponse(
        Object response,
        String responseMessage
) {
}
