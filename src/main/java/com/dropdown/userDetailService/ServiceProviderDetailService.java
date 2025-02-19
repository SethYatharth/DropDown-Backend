package com.dropdown.userDetailService;

import com.dropdown.repository.ServiceProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceProviderDetailService implements UserDetailsService {

    private final ServiceProviderRepository serviceProviderRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return serviceProviderRepository.findByEmailIgnoreCase(email).orElseThrow(() -> new UsernameNotFoundException("Service Provider with "+email+" Not Found"));
    }

}
