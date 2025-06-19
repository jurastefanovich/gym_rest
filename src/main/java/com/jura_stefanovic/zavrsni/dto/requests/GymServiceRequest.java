package com.jura_stefanovic.zavrsni.dto.requests;

import com.jura_stefanovic.zavrsni.constants.Exercise;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    @Nullable
    List<String> exercises;

}
