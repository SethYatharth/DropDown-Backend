package com.dropdown.dto;

import com.dropdown.entity.GPSLocation;

public record WebSocketMessage (
        String token,
        GPSLocation location
){
}
