package com.jura_stefanovic.zavrsni.controller;

import com.jura_stefanovic.zavrsni.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

//    @GetMapping("/available-times")
//    public ResponseEntity<List<String>> getAvailableTimes(
//            @RequestParam String date,
//            @RequestParam Long trainerId
//    ) {
//        List<LocalTime> times = scheduleService.getAvailableTimes(date, trainerId);
//        List<String> formatted = times.stream()
//                .map(t -> t.format(DateTimeFormatter.ofPattern("HH:mm")))
//                .toList();
//        return ResponseEntity.ok(formatted);
//    }

}
