package com.jura_stefanovic.zavrsni.dto.response;

import com.jura_stefanovic.zavrsni.constants.Exercise;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ExerciseRepsSummaryDTO {
    private Exercise exercise;
    private Long totalReps;

    public ExerciseRepsSummaryDTO(Exercise exercise, Long totalReps) {
        this.exercise = exercise;
        this.totalReps = totalReps;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public Long getTotalReps() {
        return totalReps;
    }
}
