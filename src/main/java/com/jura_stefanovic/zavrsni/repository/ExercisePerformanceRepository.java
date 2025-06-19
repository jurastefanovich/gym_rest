package com.jura_stefanovic.zavrsni.repository;

import com.jura_stefanovic.zavrsni.model.entity.ExercisePerformance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExercisePerformanceRepository  extends JpaRepository<ExercisePerformance, Long> {
}
