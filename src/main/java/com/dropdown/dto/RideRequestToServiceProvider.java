package com.dropdown.dto;

import lombok.Builder;

@Builder
public record RideRequestToServiceProvider(
        String id,
        String destinationLocation,
        Double distanceToPickUp,
        UserDto requestingUser
) {
}
