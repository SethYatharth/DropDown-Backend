package com.dropdown.config;

import com.dropdown.userDetailService.ServiceProviderDetailService;
import com.dropdown.userDetailService.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {

    private final UserDetailService userDetailService;
    private final ServiceProviderDetailService serviceProviderDetailService;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {

        DaoAuthenticationProvider userDaoAuthenticationProvider = new DaoAuthenticationProvider();
        userDaoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        userDaoAuthenticationProvider.setUserDetailsService(userDetailService);


        DaoAuthenticationProvider serviceProviderDaoAuthenticationProvider = new DaoAuthenticationProvider();
        serviceProviderDaoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        serviceProviderDaoAuthenticationProvider.setUserDetailsService(serviceProviderDetailService);

        return new ProviderManager(List.of(userDaoAuthenticationProvider, serviceProviderDaoAuthenticationProvider));
    }

}
