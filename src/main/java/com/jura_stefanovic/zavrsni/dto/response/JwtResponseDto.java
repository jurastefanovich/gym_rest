package com.jura_stefanovic.zavrsni.dto.response;

public class JwtResponseDto {
    private String token;
    private String refreshToken;

    public JwtResponseDto(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public static JwtResponseDto of(String token, String refreshToken){
        return new JwtResponseDto(token, refreshToken);
    }

    public String getToken() {
        return token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
