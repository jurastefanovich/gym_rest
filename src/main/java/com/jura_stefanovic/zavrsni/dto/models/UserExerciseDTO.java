package com.jura_stefanovic.zavrsni.dto.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserExerciseDTO {
    private Map<String, ExerciseDataDTO> exercises;
}
