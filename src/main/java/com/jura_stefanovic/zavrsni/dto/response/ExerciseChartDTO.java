package com.jura_stefanovic.zavrsni.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseChartDTO {
    private List<String> labels;
    private List<Integer> values;
    private String selectedExercise;
    private String timeframe;
}
