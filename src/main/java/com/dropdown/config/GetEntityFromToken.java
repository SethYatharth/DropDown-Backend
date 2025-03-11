package com.dropdown.config;


import com.dropdown.repository.ServiceProviderRepository;
import com.dropdown.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetEntityFromToken {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ServiceProviderRepository serviceProviderRepository;

    public UserDetails getEntityFromToken(String token) {
        String email = jwtService.extractEmail(token);
        String role = jwtService.extractRole(token);
        if (role.equals("USER")) {
            return userRepository.findByEmailIgnoreCase(email).orElseThrow(()->new UsernameNotFoundException(email+" not found"));
        }
        if (role.equals("SERVICE_PROVIDER")) {
            return serviceProviderRepository.findByEmailIgnoreCase(email).orElseThrow(()->new UsernameNotFoundException(email+" not found"));
        }
        return null;
    }

}
