package com.jura_stefanovic.zavrsni.dto.models.extensions;

import com.jura_stefanovic.zavrsni.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class UserBasic {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;

    public UserBasic(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
    }
}
