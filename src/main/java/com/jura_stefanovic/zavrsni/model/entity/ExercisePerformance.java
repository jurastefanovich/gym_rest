package com.jura_stefanovic.zavrsni.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jura_stefanovic.zavrsni.constants.Exercise;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Data
@Table(name = "exercise_performance")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ExercisePerformance implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Exercise exercise;

    private Integer reps;

    private Integer sets;

    private Integer weight;

    private Integer duration;

    private Integer restTime;

    @ManyToOne
    @JoinColumn(name = "statistics_id", nullable = false)
    @JsonIgnore
    private Statistics statistics;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Nullable
    @JsonIgnore
    private User user;

    @Override
    public String toString() {
        return "ExercisePerformance{" +
                "id=" + id +
                ", reps=" + reps +
                ", sets=" + sets +
                ", weight=" + weight +
                ", duration=" + duration +
                ", restTime=" + restTime +
                '}';
    }
}
