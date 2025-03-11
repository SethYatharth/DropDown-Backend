package com.dropdown.service;

import com.dropdown.entity.GPSLocation;
import org.springframework.stereotype.Service;

@Service
public class DistanceService {

    private final double EARTH_RADIUS_KM = 6371.0;

    public Double calculateDistance(GPSLocation userLocation,GPSLocation serviceProviderLocation) {
        double lat1Rad = Math.toRadians(userLocation.getLatitude());
        double lon1Rad = Math.toRadians(userLocation.getLongitude());
        double lat2Rad = Math.toRadians(serviceProviderLocation.getLatitude());
        double lon2Rad = Math.toRadians(serviceProviderLocation.getLongitude());

        // Compute differences
        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;

        // Haversine formula
        double a = Math.pow(Math.sin(deltaLat / 2), 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.pow(Math.sin(deltaLon / 2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Calculate the distance
        return EARTH_RADIUS_KM * c;
    }

}
