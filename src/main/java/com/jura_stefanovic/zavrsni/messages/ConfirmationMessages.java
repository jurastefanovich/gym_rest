package com.jura_stefanovic.zavrsni.messages;

public enum ConfirmationMessages {
    ACCOUNT_CREATED_SUCCESSFULLY("Account created successfully."),
    LOGIN_SUCCESSFUL("Logged in successfully."),
    LOGOUT_SUCCESSFUL("Logged out successfully."),
    PASSWORD_CHANGED_SUCCESSFULLY("Password changed successfully."),
    EMAIL_CONFIRMED("Email confirmed successfully."),
    PROFILE_UPDATED("Profile updated successfully."),
    ACCOUNT_DELETED("Account deleted successfully."),
    DATA_SAVED("Data saved successfully."),
    OPERATION_SUCCESSFUL("Operation completed successfully."),
    PASSWORD_RESET_LINK_SENT("Password reset link sent successfully."),
    PASSWORD_RESET_SUCCESSFUL("Password reset successfully."),
    VERIFICATION_CODE_SENT("Verification code sent successfully.");

    private final String message;

    ConfirmationMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
