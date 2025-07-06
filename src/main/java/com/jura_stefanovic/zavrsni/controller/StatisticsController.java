package com.jura_stefanovic.zavrsni.controller;

import com.jura_stefanovic.zavrsni.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stats")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/quick_stats/{id}")
    public ResponseEntity<?> getQuickStats(@PathVariable @Nullable Long id){
        return statisticsService.getQuickStats(id);
    }

    @GetMapping("/last/{num}/{id}")
    public ResponseEntity<?> getLastNumber(@PathVariable int num, @PathVariable @Nullable Long id ) {
        return statisticsService.getLastAppointments(num,id);
    }

    @GetMapping("/breakdown/{id}")
    public ResponseEntity<?> getBreakDown(@PathVariable @Nullable Long id) {
        return statisticsService.getBreakDown(id);
    }

    @GetMapping("/chart/{id}")
    public ResponseEntity<?> getExerciseChart(
            @PathVariable @Nullable Long id,
            @RequestParam(name = "exercise") String exercise,
            @RequestParam(name = "timeframe", defaultValue = "week") String timeframe) {
        return statisticsService.buildExerciseChart(exercise, timeframe, id);
    }

    @GetMapping("/goals/{id}")
    public ResponseEntity<?> getUserGoals(@PathVariable @Nullable Long id) {
        return statisticsService.getUserGoals(id);
    }

}
