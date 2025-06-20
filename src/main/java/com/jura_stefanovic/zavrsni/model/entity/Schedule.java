package com.jura_stefanovic.zavrsni.model.entity;

import com.jura_stefanovic.zavrsni.constants.Day;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
@Table(name = "schedule")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Schedule implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Day day;

    private LocalDateTime start;

    private LocalDateTime end;

    private boolean working;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id")
    @Nullable
    private User trainer;
}
