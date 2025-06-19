package com.jura_stefanovic.zavrsni.dto.models.gym;

import com.jura_stefanovic.zavrsni.constants.Exercise;
import com.jura_stefanovic.zavrsni.dto.models.users.UserDto;
import com.jura_stefanovic.zavrsni.dto.models.extensions.GymServiceExtension;
import com.jura_stefanovic.zavrsni.model.entity.GymService;
import com.jura_stefanovic.zavrsni.model.entity.User;
import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class GymServiceDetailsDto extends GymServiceExtension {
    @Nullable
    private UserDto trainer;
    private boolean needsTrainer;
    private boolean individual;
    private Integer maxUsersPerGroupSession;
    private List<String> exercises;

    public GymServiceDetailsDto(GymService gymService, List<String> exercises) {
        super(gymService);
        if (gymService.getTrainer() != null) {
            this.trainer = new UserDto(gymService.getTrainer());
        }
        this.needsTrainer = gymService.isNeedsTrainer();
        this.individual = gymService.isIndividual();
        this.maxUsersPerGroupSession = gymService.getMaxUsersPerGroupSession();
        this.exercises = exercises;
    }

    public GymServiceDetailsDto(GymService gymService, User trainer) {
        super(gymService);
        if (gymService.getTrainer() != null) {
            this.trainer = new UserDto(trainer);
        }
        this.needsTrainer = gymService.isNeedsTrainer();
        this.individual = gymService.isIndividual();
    }

    private String formatEnumName(String name) {
        return Arrays.stream(name.split("_"))
                .map(part -> part.charAt(0) + part.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }



}
