package com.jura_stefanovic.zavrsni.dto.models.gym;

import com.jura_stefanovic.zavrsni.dto.models.extensions.GymServiceExtension;
import com.jura_stefanovic.zavrsni.model.entity.GymService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
public class GymServiceDto extends GymServiceExtension {
    private Integer maxUsersPerGroupSession;
    public GymServiceDto(GymService gymService) {
        super(gymService);
        this.maxUsersPerGroupSession = gymService.getMaxUsersPerGroupSession();
    }

}
