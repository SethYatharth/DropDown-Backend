package com.dropdown.dto;

public record RideAcceptRejectRequest(
        String rideId,
        Boolean accept
) {
}
