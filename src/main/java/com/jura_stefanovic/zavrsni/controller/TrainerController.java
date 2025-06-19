package com.jura_stefanovic.zavrsni.controller;

import com.jura_stefanovic.zavrsni.dto.requests.EmployeeScheduleRequest;
import com.jura_stefanovic.zavrsni.dto.requests.TrainerRequest;
import com.jura_stefanovic.zavrsni.service.TrainerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trainers")
public class TrainerController {

    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @PostMapping
    public ResponseEntity<?> addTrainer(@RequestBody TrainerRequest request) {
       return trainerService.addTrainer(request);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return trainerService.getAll();
    }

    @PostMapping("/{trainerId}/schedules")
    public ResponseEntity<?> addSchedulesToTrainer(@PathVariable Long trainerId,
                                                   @RequestBody List<EmployeeScheduleRequest> schedules) {
        return trainerService.addSchedulesToTrainer(trainerId, schedules);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return trainerService.getTrainerById(id);
    }

    @GetMapping("/introduction/{id}")
    public ResponseEntity<?> getIntroductionById(@PathVariable Long id) {
        return trainerService.getIntroductionById(id);
    }
}
