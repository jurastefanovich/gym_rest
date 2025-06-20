package com.jura_stefanovic.zavrsni.controller;

import com.jura_stefanovic.zavrsni.dto.models.PerformanceDto;
import com.jura_stefanovic.zavrsni.dto.models.appointment.AppointmentPutDto;
import com.jura_stefanovic.zavrsni.dto.requests.FinishAppointmentRequestDTO;
import com.jura_stefanovic.zavrsni.dto.requests.GroupAppointmentRequest;
import com.jura_stefanovic.zavrsni.dto.requests.ServiceAppointmentDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jura_stefanovic.zavrsni.model.entity.Appointment;
import com.jura_stefanovic.zavrsni.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/trainer/{id}")
    public ResponseEntity<?> createAppointmentForTrainer(@RequestBody Appointment appointment, @PathVariable Long id) {
        return appointmentService.createAppointmentForTrainer(appointment, id);
    }

    // Read All
    @GetMapping
    public ResponseEntity<?> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @GetMapping("/trainer")
    public ResponseEntity<?> getAllForTrainer() {
        return appointmentService.getAllForTrainer();
    }

    // Read by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getAppointmentById(@PathVariable Long id) {
        return appointmentService.getAppointmentById(id);
    }

    @GetMapping("/calendar")
    public ResponseEntity<?> getAllForCalendar() {
        return appointmentService.getAllForCalendar();
    }
    @GetMapping("/participants/{id}")
    public ResponseEntity<?> getAllParticipants(@PathVariable Long id) {
        return appointmentService.getAllParticipants(id);
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAppointment(@PathVariable Long id, @RequestBody AppointmentPutDto updated) {
        return appointmentService.updateAppointment(id, updated);
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<?> cancelAppointment(@PathVariable Long id) {
        return appointmentService.cancelAppointment(id);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    // Get by Trainer ID
    @GetMapping("/trainer/{trainerId}")
    public ResponseEntity<?>  getAppointmentsByTrainer(@PathVariable Long trainerId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByTrainerId(trainerId));
    }

    // Get by Date Range
    @GetMapping("/between")
    public ResponseEntity<?>  getAppointmentsBetween(
            @RequestParam("start") LocalDateTime start,
            @RequestParam("end") LocalDateTime end) {
        return ResponseEntity.ok(appointmentService.getAppointmentsBetweenDates(start, end));
    }

    @GetMapping("/book/overview/{trainerId}/{serviceId}")
    public ResponseEntity<?> getOverviewDetailsForBookingAppointment(Long trainerId, Long serviceId) {
        return appointmentService.getOverviewDetailsForBookingAppointment(trainerId, serviceId);
    }

    @GetMapping("/group")
    public ResponseEntity<?> getGroupAppointments() {
        return appointmentService.getGroupAppointments();
    }

    @PostMapping("/group")
    public ResponseEntity<?> createGroupAppointment(@RequestBody GroupAppointmentRequest dto) {
        return appointmentService.createGroupAppointment(dto);
    }

    @GetMapping("/service/appointments/{serviceId}")
    public ResponseEntity<?> getActiveAppointmentsForService(@PathVariable Long serviceId) {
        return appointmentService.getActiveAppointmentsForService(serviceId);
    }

    @PostMapping("/group/join/{appointmentId}")
    public ResponseEntity<?> joinSession(@PathVariable Long appointmentId ) {
        return appointmentService.joinSession(appointmentId);
    }

    @PostMapping("/group/cancel/{appointmentId}")
    public ResponseEntity<?> removeFromSession(@PathVariable Long appointmentId) {
        return appointmentService.removeFromSession(appointmentId);
    }

    @PutMapping("/finish/{appointmentId}")
    public ResponseEntity<?> finishAppointment( @RequestBody FinishAppointmentRequestDTO requestDTO,
                                                @PathVariable Long appointmentId) {
        return appointmentService.finishAppointment(requestDTO, appointmentId);
    }

}
