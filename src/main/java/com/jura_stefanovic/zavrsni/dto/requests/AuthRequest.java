package com.jura_stefanovic.zavrsni.dto.requests;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public AuthRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
