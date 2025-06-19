package com.jura_stefanovic.zavrsni.controller;

import com.jura_stefanovic.zavrsni.dto.models.PerformanceDto;
import com.jura_stefanovic.zavrsni.service.PerformanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/performance")
public class PerformanceController {

    private final PerformanceService performanceService;

    public PerformanceController(PerformanceService performanceService) {
        this.performanceService = performanceService;
    }

    /**
     * Save a list of performances for a specific appointment.
     */
    @PostMapping("/{appointmentId}")
    public ResponseEntity<?> savePerformances(
            @PathVariable Long appointmentId,
            @RequestBody List<PerformanceDto> performances
    ) {
        return performanceService.savePerformances(appointmentId, performances);
    }

    /**
     * Update a list of performances for a specific appointment.
     */
    @PutMapping("/{appointmentId}")
    public ResponseEntity<?> updatePerformances(
            @PathVariable Long appointmentId,
            @RequestBody List<PerformanceDto> performances
    ) {
        return performanceService.updatePerformances(appointmentId, performances);
    }

    /**
     * Get all performances by appointment ID.
     */
    @GetMapping("/{appointmentId}")
    public ResponseEntity<?> getPerformancesByAppointmentId(@PathVariable Long appointmentId) {
        return performanceService.getPerformancesByAppointmentId(appointmentId);
    }

    /**
     * Delete all performances tied to an appointment.
     */
    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<?> deletePerformancesByAppointmentId(@PathVariable Long appointmentId) {
        return performanceService.deletePerformancesByAppointmentId(appointmentId);
    }
}
