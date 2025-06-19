package com.jura_stefanovic.zavrsni.dto.models;

import com.jura_stefanovic.zavrsni.constants.Exercise;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceDto {

    @Enumerated(EnumType.STRING)
    private Exercise exercise;

    private Integer reps;

    private Integer sets;

    private Integer weight;

    private Integer duration;

    private Integer restTime;
}
