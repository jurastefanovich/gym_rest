package com.jura_stefanovic.zavrsni.repository;
import com.jura_stefanovic.zavrsni.model.entity.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticsRepository  extends JpaRepository<Statistics, Long> {
}
