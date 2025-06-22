package com.jura_stefanovic.zavrsni.repository;

import com.jura_stefanovic.zavrsni.constants.Exercise;
import com.jura_stefanovic.zavrsni.model.entity.ExercisePerformance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExercisePerformanceRepository  extends JpaRepository<ExercisePerformance, Long> {
    @Query("""
    SELECT ep.exercise, COUNT(ep) AS cnt
    FROM ExercisePerformance ep
    WHERE ep.user.id = :userId
    GROUP BY ep.exercise
    ORDER BY cnt DESC
""")
    List<Exercise> findMostDoneExercisesForUser(@Param("userId") Long userId);

    @Query("SELECT ep.exercise, SUM(ep.reps) " +
            "FROM ExercisePerformance ep " +
            "WHERE ep.user.id = :userId " +
            "GROUP BY ep.exercise order by SUM(ep.reps) DESC ")
    List<Object[]> getTotalRepsPerExercise(Long userId);

    List<ExercisePerformance> findByUserId(Long userId);
}
