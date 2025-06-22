package com.jura_stefanovic.zavrsni.manager;

import com.jura_stefanovic.zavrsni.constants.Exercise;
import com.jura_stefanovic.zavrsni.model.entity.ExercisePerformance;
import com.jura_stefanovic.zavrsni.repository.ExercisePerformanceRepository;
import com.jura_stefanovic.zavrsni.repository.PerformanceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@AllArgsConstructor
public class ExercisePerformanceManager {
    private final PerformanceRepository performanceRepository;
    private final ExercisePerformanceRepository exercisePerformanceRepository;

    public List<Exercise> getFavoriteExercise(Long userId) {
        return exercisePerformanceRepository.findMostDoneExercisesForUser(userId);
    }

    public List<ExercisePerformance> findByUserId(Long userId) {
        return exercisePerformanceRepository.findByUserId(userId);
    }

    public List<Object[]> getTotalRepsPerExercise(Long userId) {
        return exercisePerformanceRepository.getTotalRepsPerExercise(userId);
    }

}
