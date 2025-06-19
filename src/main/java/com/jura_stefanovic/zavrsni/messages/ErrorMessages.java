package com.jura_stefanovic.zavrsni.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;

public enum ErrorMessages {

    INVALID_CREDENTIALS("Invalid username or password."),
    ACCOUNT_DISABLED("Your account is disabled."),
    ACCOUNT_LOCKED("Your account is locked."),
    ACCOUNT_NOT_FOUND("Account not found."),
    EMAIL_ALREADY_IN_USE("This email is already in use."),
    USERNAME_ALREADY_TAKEN("This username is already taken."),
    PASSWORDS_DO_NOT_MATCH("Passwords do not match."),
    MISSING_PASSWORD("Missing password"),
    UNAUTHORIZED_ACCESS("You are not authorized to access this resource."),
    FORBIDDEN_ACTION("You do not have permission to perform this action."),
    RESOURCE_NOT_FOUND("The requested resource was not found."),
    INTERNAL_SERVER_ERROR("An unexpected error occurred. Please try again later."),
    INVALID_TOKEN("Invalid or expired token."),
    TOKEN_EXPIRED("Your session has expired. Please log in again."),
    BAD_REQUEST("The request is invalid or malformed."),
    MISSING_REQUIRED_FIELDS("Required fields are missing or invalid."),
    PASSWORD_TOO_WEAK("The provided password is too weak.");

    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
