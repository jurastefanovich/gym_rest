package com.jura_stefanovic.zavrsni.dto.requests;

public class JwtRefreshRequest {
    private String refreshToken;

    public JwtRefreshRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
