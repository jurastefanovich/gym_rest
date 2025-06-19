package com.jura_stefanovic.zavrsni.dto.models.extensions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jura_stefanovic.zavrsni.constants.Role;
import com.jura_stefanovic.zavrsni.model.entity.User;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class UserExtension {
    @Nullable
    private String username;
    private String firstName;
    private String lastName;
    @Nullable
    private String phoneNumber;
    private Long id;
    private String email;
    @Nullable
    private String initials;
    private Role role;
    public UserExtension(User user) {
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.phoneNumber = user.getPhoneNumber();
        this.id = user.getId();
        this.email = user.getEmail();
        this.initials = user.getInitials();
        this.role = user.getRole();
    }
}
