package com.dropdown.controller;

import com.dropdown.dto.BaseResponse;
import com.dropdown.dto.LoginRequest;
import com.dropdown.dto.RegistrationRequest;
import com.dropdown.exception.ServiceProviderException;
import com.dropdown.exception.UserException;
import com.dropdown.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<BaseResponse> register(@RequestBody RegistrationRequest registrationRequest) {
        return ResponseEntity.ok(authService.register(registrationRequest));
    }

    @GetMapping("/valid/{token}")
    public ResponseEntity<BaseResponse> isValid(@PathVariable String token) {
        return new ResponseEntity<>(authService.checkIsValid(token), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse> login(@RequestBody LoginRequest loginRequest) throws UserException, ServiceProviderException {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @GetMapping("/{refreshToken}")
    public ResponseEntity<BaseResponse> refresh(@PathVariable String refreshToken) throws ServiceProviderException, UserException {
        System.out.println(refreshToken);
        return ResponseEntity.ok(authService.refresh(refreshToken));
    }

}
