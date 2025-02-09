package com.dropdown.config;

import com.dropdown.userDetailService.ServiceProviderDetailService;
import com.dropdown.userDetailService.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailResolver {

    private final UserDetailService userDetailsService;
    private final ServiceProviderDetailService serviceProviderDetailService;

    public UserDetails resolveUserDetails(String role,String email) {
        return switch (role){
            case "USER" -> userDetailsService.loadUserByUsername(email);
            case "SERVICE_PROVIDER" -> serviceProviderDetailService.loadUserByUsername(email);
            default -> throw new IllegalStateException("Unexpected value: " + role);
        };
    }

}
