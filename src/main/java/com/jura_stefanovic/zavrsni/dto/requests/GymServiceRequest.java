package com.jura_stefanovic.zavrsni.dto.requests;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GymServiceRequest {

    private String title;
    private String description;
    @Nullable
    private Long durationSeconds;
    @Nullable
    private Long trainerId;
    private Integer maxUsersPerGroupSession;
    private boolean individual;
    private boolean trainerRequired;

}
