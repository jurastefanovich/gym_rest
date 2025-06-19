package com.jura_stefanovic.zavrsni.manager;

import com.jura_stefanovic.zavrsni.repository.PerformanceRepository;
import org.springframework.stereotype.Component;

@Component
public class ExercisePerformanceManager {
    private final PerformanceRepository performanceRepository;

    public ExercisePerformanceManager(PerformanceRepository performanceRepository) {
        this.performanceRepository = performanceRepository;
    }
}
