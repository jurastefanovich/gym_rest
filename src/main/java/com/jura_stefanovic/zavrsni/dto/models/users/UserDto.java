package com.jura_stefanovic.zavrsni.dto.models.users;

import com.jura_stefanovic.zavrsni.dto.models.extensions.UserExtension;
import com.jura_stefanovic.zavrsni.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
public class UserDto extends UserExtension {
    public UserDto(User user) {
        super(user);
    }
}
