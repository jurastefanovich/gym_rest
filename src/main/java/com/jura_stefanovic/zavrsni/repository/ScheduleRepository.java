package com.jura_stefanovic.zavrsni.repository;

import com.jura_stefanovic.zavrsni.constants.Day;
import com.jura_stefanovic.zavrsni.model.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByTrainerId(Long trainerId);

    List<Schedule> findByTrainerIdAndDay(Long trainerId, Day day);
}
