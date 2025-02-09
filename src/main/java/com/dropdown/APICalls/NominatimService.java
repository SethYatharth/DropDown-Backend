package com.dropdown.APICalls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class NominatimService {

    private static final String NOMINATIM_URL = "https://nominatim.openstreetmap.org/reverse?format=json&lat={latitude}&lon={longitude}";

    private final RestTemplate restTemplate;

    public String getCityName(double latitude, double longitude) {
        // Call the Nominatim API
        NominatimResponse response = restTemplate.getForObject(
                NOMINATIM_URL,
                NominatimResponse.class,
                latitude,
                longitude
        );

        // Extract the city name from the response
        if (response != null && response.getAddress() != null) {
            if (response.getAddress().getCity() != null) {
                return response.getAddress().getCity();
            } else if (response.getAddress().getTown() != null) {
                return response.getAddress().getTown();
            } else if (response.getAddress().getVillage() != null) {
                return response.getAddress().getVillage();
            }
        }

        return "City not found";
    }
}