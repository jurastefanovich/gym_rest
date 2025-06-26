package com.jura_stefanovic.zavrsni.controller;

import com.jura_stefanovic.zavrsni.dto.response.GymResponseDto;
import com.jura_stefanovic.zavrsni.service.GymManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gym")
public class GymController {

    private final GymManagementService gymService;

    public GymController(GymManagementService gymService) {
        this.gymService = gymService;
    }
    @GetMapping
    public ResponseEntity<?> getGymDataForDashBoard(@RequestParam(defaultValue = "dash_board") String val) {
        return gymService.getGymDataByView(val);
    }

    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashBoard() {
        return gymService.getDashboard();
    }

    @GetMapping("/members")
    public ResponseEntity<?> getMembers() {
        return gymService.getMembers();
    }

    @GetMapping("/trainers")
    public ResponseEntity<?> getTrainers() {
        return gymService.getTrainers();
    }

    @GetMapping("/gym_info")
    public ResponseEntity<?> getGymInfo() {
        return gymService.getGymInfo();
    }

    @GetMapping("/specializations")
    public List<String> getSpecializations() {
        return gymService.getSpecializations();
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveGym(@RequestBody GymResponseDto dto) {
        return gymService.saveGym(dto);
    }
 }

