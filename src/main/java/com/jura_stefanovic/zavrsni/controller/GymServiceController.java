package com.jura_stefanovic.zavrsni.controller;

import com.jura_stefanovic.zavrsni.dto.models.gym.GymServiceDto;
import com.jura_stefanovic.zavrsni.dto.requests.GymServiceRequest;
import com.jura_stefanovic.zavrsni.service.GymServiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gym-services")
public class GymServiceController {

    private final GymServiceService gymServiceService;

    public GymServiceController(GymServiceService gymServiceService) {
        this.gymServiceService = gymServiceService;
    }

    @GetMapping
    public List<GymServiceDto> getAll() {
        return gymServiceService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return gymServiceService.getById(id);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody GymServiceRequest request) {
        return gymServiceService.create(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody GymServiceRequest request) {
        return gymServiceService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return gymServiceService.delete(id);
    }

    @GetMapping("/group")
    public ResponseEntity<?> getGroupServices() {
        return gymServiceService.getGroupServices();
    }
}
