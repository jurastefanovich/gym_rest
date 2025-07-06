package com.jura_stefanovic.zavrsni.controller;

import com.jura_stefanovic.zavrsni.dto.requests.AuthRequest;
import com.jura_stefanovic.zavrsni.dto.requests.RegRequest;
import com.jura_stefanovic.zavrsni.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        return authService.authenticate(request);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegRequest request) {
        return authService.register(request);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> body) {
        return authService.refresh(body);
    }

    @PostMapping("/logout")
    public String logout(@RequestBody String refreshRequestDto){
        return authService.logout(refreshRequestDto);
    }
}