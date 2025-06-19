package com.jura_stefanovic.zavrsni.dto.requests;

import com.jura_stefanovic.zavrsni.dto.models.ExerciseDataDTO;
import com.jura_stefanovic.zavrsni.dto.models.ExerciseDefaultsDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FinishAppointmentRequestDTO {
    private Map<Long, Map<String, ExerciseDataDTO>> userExerciseData;
    private Map<String, ExerciseDefaultsDTO> exerciseDefaults;
}
