package com.dropdown.service;

import com.dropdown.APICalls.Coordinates;
import com.dropdown.APICalls.H3UberGridService;
import com.dropdown.config.JwtService;
import com.dropdown.dto.AuthResponse;
import com.dropdown.dto.BaseResponse;
import com.dropdown.dto.LoginRequest;
import com.dropdown.dto.RegistrationRequest;
import com.dropdown.entity.GPSLocation;
import com.dropdown.entity.Role;
import com.dropdown.entity.ServiceProvider;
import com.dropdown.entity.User;
import com.dropdown.exception.ServiceProviderException;
import com.dropdown.exception.UserException;
import com.dropdown.repository.ServiceProviderRepository;
import com.dropdown.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ServiceProviderRepository serviceProviderRepository;
    private final H3UberGridService h3UberGridService;

    @Value("${auth.success.registration}")
    private String registrationSuccess;
    @Value("${auth.success.login}")
    private String loginSuccess;
    @Value("${auth.failure.registration}")
    private String registrationFailed;
    @Value("${auth.failure.login}")
    private String loginFailed;
    @Value("${jwt.expired.refresh}")
    private String refreshTokenExpired;


    public BaseResponse register(RegistrationRequest request) {
        if (request.role().equals("User")) {
            var user = User.builder()
                    .name(request.name())
                    .email(request.email())
                    .password(passwordEncoder.encode(request.password()))
                    .phoneNo(request.phoneNo())
                    .role(Role.USER)
//                    .location(
//                            new GPSLocation(
//                                    request.latitude(),
//                                    request.longitude(),
//                                    h3UberGridService.getGridAddress(
//                                            new Coordinates(
//                                                    request.latitude(), request.longitude()
//                                            )
//                                    )
//                            )
//                    )
                    .build();
            User user1 = userRepository.save(user);
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            return BaseResponse.builder()
                    .response(new AuthResponse(user1.getId(),jwtService.generateToken(user), jwtService.generateRefreshToken(user)))
                    .responseMessage(registrationSuccess)
                    .build();
        } else if (request.role().equals("ServiceProvider")) {
            var serviceProvider = ServiceProvider.builder()
                    .name(request.name())
                    .email(request.email())
                    .password(passwordEncoder.encode(request.password()))
                    .phoneNo(request.phoneNo())
                    .role(Role.SERVICE_PROVIDER)
//                    .location(
//                            new GPSLocation(
//                                    request.latitude(),
//                                    request.longitude(),
//                                    h3UberGridService.getGridAddress(
//                                            new Coordinates(
//                                                    request.latitude(), request.longitude()
//                                            )
//                                    )
//                            )
//                    )
                    .build();
            ServiceProvider serviceProvider1 = serviceProviderRepository.save(serviceProvider);
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            return BaseResponse.builder()
                    .response(new AuthResponse(serviceProvider1.getId(),jwtService.generateToken(serviceProvider), jwtService.generateRefreshToken(serviceProvider)))
                    .responseMessage(registrationSuccess)
                    .build();
        } else {
            return BaseResponse.builder()
                    .responseMessage(registrationFailed)
                    .build();
        }
    }

    public BaseResponse login(LoginRequest request) throws UserException, ServiceProviderException {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        if (request.role().equals("User")) {
            User user = userRepository.findByEmailIgnoreCase(request.email()).orElseThrow(() -> new UserException("User Not Found with "+request.email()));

//            user.setLocation(
//                        new GPSLocation(
//                                request.latitude(),
//                                request.longitude(),
//                                h3UberGridService.getGridAddress(
//                                        new Coordinates(
//                                                request.latitude(),
//                                                request.longitude()
//                                        )
//                                )
//                        )
//                );
//            User user1 = userRepository.save(user);

            return BaseResponse.builder()
                    .response(new AuthResponse(user.getId(),jwtService.generateToken(user), jwtService.generateRefreshToken(user)))
                    .responseMessage(loginSuccess)
                    .build();
        } else if (request.role().equals("ServiceProvider")) {
            ServiceProvider serviceProvider = serviceProviderRepository.findByEmailIgnoreCase(request.email()).orElseThrow(() -> new ServiceProviderException("Service Provider Not Found with "+request.email()));
//                serviceProvider.setLocation(
//                        new GPSLocation(
//                                request.latitude(),
//                                request.longitude(),
//                                h3UberGridService.getGridAddress(
//                                        new Coordinates(
//                                                request.latitude(),
//                                                request.longitude()
//                                        )
//                                )
//                        )
//                );
//            ServiceProvider serviceProvider1 = serviceProviderRepository.save(serviceProvider);

            System.out.println(serviceProvider.getEmail());
            return BaseResponse.builder()
                    .response(new AuthResponse(serviceProvider.getId(),jwtService.generateToken(serviceProvider), jwtService.generateRefreshToken(serviceProvider)))
                    .responseMessage(loginSuccess)
                    .build();
        } else {
            return BaseResponse.builder()
                    .responseMessage(loginFailed)
                    .build();
        }
    }

    public BaseResponse refresh(String refreshToken) throws UserException, ServiceProviderException {
        if (jwtService.isTokenExpired(refreshToken)) {
            throw new RuntimeException(refreshTokenExpired);
        }
        String role = jwtService.extractRole(refreshToken);
        String email = jwtService.extractEmail(refreshToken);
        if (role.equals("USER")) {
            User user = userRepository.findByEmailIgnoreCase(email).orElseThrow(() -> new UserException("User Not Found"));
            return BaseResponse.builder()
                    .response(new AuthResponse(user.getId(),jwtService.generateToken(user), jwtService.generateRefreshToken(user)))
                    .responseMessage(loginSuccess)
                    .build();
        } else if (role.equals("SERVICE_PROVIDER")) {
            ServiceProvider serviceProvider = serviceProviderRepository.findByEmailIgnoreCase(email).orElseThrow(() -> new ServiceProviderException("Service Provider Not Found"));
            return BaseResponse.builder()
                    .response(new AuthResponse(serviceProvider.getId(),jwtService.generateToken(serviceProvider), jwtService.generateRefreshToken(serviceProvider)))
                    .responseMessage(loginSuccess)
                    .build();
        } else {
            return BaseResponse.builder()
                    .responseMessage(loginFailed)
                    .build();
        }
    }

    public BaseResponse checkIsValid(String token) {
        return BaseResponse.builder()
                .response(!jwtService.isTokenExpired(token))
                .responseMessage("Check Performed")
                .build();
    }
}

