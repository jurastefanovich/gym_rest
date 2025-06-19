package com.jura_stefanovic.zavrsni.service;

import com.jura_stefanovic.zavrsni.dto.requests.JwtRefreshRequest;
import com.jura_stefanovic.zavrsni.dto.response.ErrorResponse;
import com.jura_stefanovic.zavrsni.dto.response.JwtResponseDto;
import com.jura_stefanovic.zavrsni.manager.UserManager;
import com.jura_stefanovic.zavrsni.model.auth.RefreshToken;
import com.jura_stefanovic.zavrsni.model.entity.User;
import com.jura_stefanovic.zavrsni.repository.RefreshTokenRepository;
import com.jura_stefanovic.zavrsni.security.JwtUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Ref;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserManager userManager;
    private final JwtUtil jwtUtil;
    private String TOKEN_SECRET = "~v7!sEub}~HT]9+D]7%eJ,o>0*xH8D-2";
    private final Map<String, String> refreshTokenStorage = new ConcurrentHashMap<>();

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, UserManager userManager, JwtUtil jwtUtil) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userManager = userManager;
        this.jwtUtil = jwtUtil;
    }

    @PostConstruct
    protected void init(){
        TOKEN_SECRET = Base64.getEncoder().encodeToString(TOKEN_SECRET.getBytes());
    }

    public RefreshToken createRefreshToken(String loginCredential) {
        deleteExpiredTokens(loginCredential);

        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .expiration(LocalDateTime.now().plusHours(6)) // ✅ Simplified
                .login(loginCredential)
                .build();

        return refreshTokenRepository.save(refreshToken);
    }


    public ResponseEntity<?> refreshToken(JwtRefreshRequest refreshRequestDto) {

        Optional<RefreshToken> tokenOptional = refreshTokenRepository.findRefreshTokenByToken(refreshRequestDto.getRefreshToken());

        if (!tokenOptional.isPresent()) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        RefreshToken token = tokenOptional.get();
        if (isTokenExpired(token.getExpiration())) {
            refreshTokenRepository.delete(token);
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        User userOptional = userManager.findByEmail(token.getLogin());

        if(userOptional == null) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        String jwt = jwtUtil.createToken(new HashMap<>(), token.getLogin());
        refreshTokenRepository.save(token);
        return ResponseEntity.ok(JwtResponseDto.of(jwt, token.getToken()));
    }

    private boolean isTokenExpired(LocalDateTime expirationTime){
        return expirationTime.isBefore(LocalDateTime.now());
    }

    public String logout(JwtRefreshRequest refreshToken) {
        Optional<RefreshToken> refreshTokenOptional = refreshTokenRepository.findRefreshTokenByToken(refreshToken.getRefreshToken());
        RefreshToken token = refreshTokenOptional.get();

        if (refreshTokenOptional.isPresent()){
            refreshTokenRepository.delete(token);
            return "OK";
        }

        return "Error";
    }

    private void deleteExpiredTokens(String email) {
        List<RefreshToken> expiredRefreshTokens = refreshTokenRepository.findExpiredRefreshTokensByEmail(email, LocalDateTime.now());
        if (!expiredRefreshTokens.isEmpty()) {
            refreshTokenRepository.deleteAll(expiredRefreshTokens);
        }
    }

    public String getEmailFromToken(String email) {
        RefreshToken  refreshToken = refreshTokenRepository.findByUserEmail(email).orElseThrow(()-> new RuntimeException("Email was not found"));
        return refreshToken.getLogin();
    }
}
