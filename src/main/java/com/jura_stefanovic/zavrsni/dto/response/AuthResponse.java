package com.jura_stefanovic.zavrsni.dto.response;

import com.jura_stefanovic.zavrsni.constants.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String fullName;
    private String accessToken;
    private String refreshToken;
    private Long id;
    private Role role;
}
