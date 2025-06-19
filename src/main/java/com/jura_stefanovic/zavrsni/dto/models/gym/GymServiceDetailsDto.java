package com.jura_stefanovic.zavrsni.dto.models.gym;

import com.jura_stefanovic.zavrsni.dto.models.users.UserDto;
import com.jura_stefanovic.zavrsni.dto.models.extensions.GymServiceExtension;
import com.jura_stefanovic.zavrsni.model.entity.GymService;
import com.jura_stefanovic.zavrsni.model.entity.User;
import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GymServiceDetailsDto extends GymServiceExtension {
    @Nullable
    private UserDto trainer;
    private boolean needsTrainer;
    private boolean individual;
    private Integer maxUsersPerGroupSession;

    public GymServiceDetailsDto(GymService gymService) {
        super(gymService);
        if (gymService.getTrainer() != null) {
            this.trainer = new UserDto(gymService.getTrainer());
        }
        this.needsTrainer = gymService.isNeedsTrainer();
        this.individual = gymService.isIndividual();
        this.maxUsersPerGroupSession = gymService.getMaxUsersPerGroupSession();
    }

    public GymServiceDetailsDto(GymService gymService, User trainer) {
        super(gymService);
        if (gymService.getTrainer() != null) {
            this.trainer = new UserDto(trainer);
        }
        this.needsTrainer = gymService.isNeedsTrainer();
        this.individual = gymService.isIndividual();
    }



}
