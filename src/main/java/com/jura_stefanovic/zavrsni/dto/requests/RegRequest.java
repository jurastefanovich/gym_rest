package com.jura_stefanovic.zavrsni.dto.requests;

import lombok.Data;

@Data
public class RegRequest {
    public String password;
    public String confirmPassword;
    public String username;
    public String firstName;
    public String lastName;
    public String email;
    public String phoneNumber;

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
