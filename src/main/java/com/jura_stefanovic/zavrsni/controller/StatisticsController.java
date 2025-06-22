package com.jura_stefanovic.zavrsni.controller;

import com.jura_stefanovic.zavrsni.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stats")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/quick_stats")
    public ResponseEntity<?> getQuickStats(){
        return statisticsService.getQuickStats();
    }

    @GetMapping("/last/{num}")
    public ResponseEntity<?> getLastNumber(@PathVariable int num) {
        return statisticsService.getLastAppointments(num);
    }

    @GetMapping("/breakdown")
    public ResponseEntity<?> getBreakDown() {
        return statisticsService.getBreakDown();
    }

    @GetMapping("/chart")
    public ResponseEntity<?> getExerciseChart(
            @RequestParam(name = "exercise") String exercise,
            @RequestParam(name = "timeframe", defaultValue = "week") String timeframe) {
        return statisticsService.buildExerciseChart(exercise, timeframe);
    }

}
