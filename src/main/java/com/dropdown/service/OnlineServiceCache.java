package com.dropdown.service;

import com.dropdown.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OnlineServiceCache {

    private final JwtService jwtService;
    private final CacheService cacheService;

    public void online(String token) {
        String email = jwtService.extractEmail(token);
        cacheService.put(email, LocalDateTime.now());
    }


    public boolean isOnline(String email) {
        cacheService.get(email);
        return cacheService.get(email) != null;
    }



}
