package com.jura_stefanovic.zavrsni.service;

import com.jura_stefanovic.zavrsni.constants.Role;
import com.jura_stefanovic.zavrsni.dto.requests.AuthRequest;
import com.jura_stefanovic.zavrsni.dto.response.AuthResponse;
import com.jura_stefanovic.zavrsni.dto.requests.RegRequest;
import com.jura_stefanovic.zavrsni.dto.response.ErrorResponse;
import com.jura_stefanovic.zavrsni.manager.UserManager;
import com.jura_stefanovic.zavrsni.messages.ConfirmationMessages;
import com.jura_stefanovic.zavrsni.messages.ErrorMessages;
import com.jura_stefanovic.zavrsni.model.auth.RefreshToken;
import com.jura_stefanovic.zavrsni.model.entity.User;
import com.jura_stefanovic.zavrsni.security.CustomUserDetailsService;
import com.jura_stefanovic.zavrsni.security.JwtUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    private final AuthenticationManager authManager;
    private final CustomUserDetailsService userDetailsService;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserManager userManager;

    public AuthService(AuthenticationManager authManager, CustomUserDetailsService userDetailsService, RefreshTokenService refreshTokenService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, UserManager userManager) {
        this.authManager = authManager;
        this.userDetailsService = userDetailsService;
        this.refreshTokenService = refreshTokenService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.userManager = userManager;
    }

    public ResponseEntity<?> authenticate(AuthRequest request) {
        User user = userManager.findByEmail(request.getEmail());

        try {
            authLogin(user, request); // Validate password and credentials
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }

        String accessToken = jwtUtil.generateToken(user);
        RefreshToken refreshTokenEntity = refreshTokenService.createRefreshToken(user.getEmail());
        String refreshToken = refreshTokenEntity.getToken();
        if (user.getLastLoginTime() != null) {
            user.setLoggedInBefore(true);
        }
        user.setLastLoginTime(LocalDateTime.now());
        userManager.save(user);

        return ResponseEntity.ok().body(new AuthResponse(user.getFullName(), accessToken, refreshToken, user.getId(), user.getRole()));
    }


    public ResponseEntity<?> register(RegRequest request) {

        try {
            authRegister(request);
        }catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }

        User newUser = new User(request);
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole(Role.USER);
        userManager.save(newUser);
        return ResponseEntity.ok().body(ConfirmationMessages.ACCOUNT_CREATED_SUCCESSFULLY.getMessage());
    }

    private void authRegister(RegRequest request) throws RuntimeException {
        User user = userManager.findByEmail(request.getEmail());
        if (user != null) {
            throw new RuntimeException(ErrorMessages.EMAIL_ALREADY_IN_USE.getMessage());
        } else if (Strings.isBlank(request.getPassword()) || Strings.isBlank(request.getConfirmPassword())) {
            throw new RuntimeException(ErrorMessages.MISSING_PASSWORD.getMessage());
        } else if (!request.getConfirmPassword().equals(request.getPassword())) {
            throw new RuntimeException(ErrorMessages.PASSWORDS_DO_NOT_MATCH.getMessage());
        }
    }

    private void authLogin(User user, AuthRequest request) throws RuntimeException {
        if (user == null) {
            throw new RuntimeException("User doesn't exist");
        }else if (Strings.isBlank(request.getPassword())) {
            throw new RuntimeException("Missing password");
        } else if (!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Incorrect credentials");
        }
    }

    public ResponseEntity<?> refresh(Map<String, String> body) {
        String refreshToken = body.get("refreshToken");

        if (refreshToken == null) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Refresh token is missing"));
        }

        try{
            String email = refreshTokenService.getEmailFromToken(refreshToken);
            if (email == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Invalid refresh token"));
            }

            User user = userManager.findByEmail(email);
            String newAccessToken = jwtUtil.generateToken(user);

            return ResponseEntity.ok(Collections.singletonMap("accessToken", newAccessToken));
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(e.getMessage()));
        }
    }

    public String logout(String refreshRequestDto) {
//        Optional<RefreshToken> refreshTokenOptional = refreshTokenRepository.findRefreshTokenByToken(refreshToken.getRefreshToken());
//        RefreshToken token = refreshTokenOptional.get();
//
//        if (refreshTokenOptional.isPresent()){
//            refreshTokenRepository.delete(token);
//            return ConfirmationMessages.LOGGED_OUT.getMessage();
//        }

        return null;
    }
}