package com.jura_stefanovic.zavrsni.manager;

import com.jura_stefanovic.zavrsni.repository.ScheduleRepository;
import org.springframework.stereotype.Component;

@Component
public class ScheduleManager {
    private final ScheduleRepository scheduleRepository;

    public ScheduleManager(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }
}
