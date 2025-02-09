package com.dropdown.controller;

import com.dropdown.dto.BaseResponse;
import com.dropdown.dto.LoginRequest;
import com.dropdown.dto.RegistrationRequest;
import com.dropdown.service.AuthService;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/login")
    public ResponseEntity<BaseResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @GetMapping("/{refreshToken}")
    public ResponseEntity<BaseResponse> refresh(@PathVariable String refreshToken) {
        System.out.println(refreshToken);
        return ResponseEntity.ok(authService.refresh(refreshToken));
    }

}
