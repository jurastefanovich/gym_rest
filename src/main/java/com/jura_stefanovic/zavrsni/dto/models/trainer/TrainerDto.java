package com.jura_stefanovic.zavrsni.dto.models.trainer;

import com.jura_stefanovic.zavrsni.dto.models.extensions.UserBasic;
import com.jura_stefanovic.zavrsni.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
public class TrainerDto extends UserBasic {
    private String description;
    private List<String> specialization;
    public TrainerDto(User user) {
        super(user);
        this.description = user.getDescription();
        this.specialization = user.getSpecialisation();
    }
}
