package com.jura_stefanovic.zavrsni.manager;

import com.jura_stefanovic.zavrsni.model.entity.Statistics;
import com.jura_stefanovic.zavrsni.repository.StatisticsRepository;
import org.springframework.stereotype.Component;

@Component
public class StatisticsManager {
    private final StatisticsRepository statisticsRepository;

    public StatisticsManager(StatisticsRepository statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
    }

    public Statistics save(Statistics statistics) {
        return statisticsRepository.save(statistics);
    }


}
