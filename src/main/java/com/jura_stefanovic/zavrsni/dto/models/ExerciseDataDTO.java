package com.jura_stefanovic.zavrsni.dto.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExerciseDataDTO {
    private Integer sets;
    private Integer reps;
    private Integer weight;
    private Integer duration;
    private Integer restTime;
    private boolean modified;
}
