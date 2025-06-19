package com.jura_stefanovic.zavrsni.service;
import com.jura_stefanovic.zavrsni.dto.models.PerformanceDto;
import com.jura_stefanovic.zavrsni.manager.AppointmentManager;
import com.jura_stefanovic.zavrsni.model.entity.Appointment;
import com.jura_stefanovic.zavrsni.model.entity.ExercisePerformance;
import com.jura_stefanovic.zavrsni.model.entity.Statistics;
import com.jura_stefanovic.zavrsni.repository.ExercisePerformanceRepository;
import com.jura_stefanovic.zavrsni.repository.StatisticsRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PerformanceService {

    private final AppointmentManager appointmentManager;
    private final StatisticsRepository statisticsRepository;
    private final ExercisePerformanceRepository performanceRepository;

    public PerformanceService(AppointmentManager appointmentManager, StatisticsRepository statisticsRepository, ExercisePerformanceRepository performanceRepository) {
        this.appointmentManager = appointmentManager;
        this.statisticsRepository = statisticsRepository;
        this.performanceRepository = performanceRepository;
    }

    public ResponseEntity<?> savePerformances(Long appointmentId, List<PerformanceDto> performances) {
        Appointment appointment = appointmentManager.findById(appointmentId);

        if (appointment == null) {
            return ResponseEntity.badRequest().body("Appointment not found");
        }

        if (appointment.getStatistics() != null) {
            return updatePerformances(appointmentId, performances);
        }

        Statistics statistics = new Statistics();
        statistics.setAppointment(appointment);

        List<ExercisePerformance> performanceList = performances.stream()
                .map(dto -> mapToEntity(dto, statistics))
                .collect(Collectors.toList());

        statistics.setPerformances(performanceList);
        statisticsRepository.save(statistics);

        // Attach the new statistics to the appointment
        appointment.setStatistics(statistics);
        appointmentManager.save(appointment);

        return ResponseEntity.ok("Performances saved successfully.");
    }

    public ResponseEntity<?> updatePerformances(Long appointmentId, List<PerformanceDto> performances) {
        Appointment appointment = appointmentManager.findById(appointmentId);

        if (appointment == null || appointment.getStatistics() == null) {
            return ResponseEntity.badRequest().body("Statistics not found for this appointment.");
        }

        Statistics statistics = appointment.getStatistics();

        // Clear old performances
        performanceRepository.deleteAll(statistics.getPerformances());

        List<ExercisePerformance> updatedPerformances = performances.stream()
                .map(dto -> mapToEntity(dto, statistics))
                .collect(Collectors.toList());

        statistics.setPerformances(updatedPerformances);
        statisticsRepository.save(statistics);

        return ResponseEntity.ok("Performances updated successfully.");
    }

    public ResponseEntity<?> getPerformancesByAppointmentId(Long appointmentId) {
        Appointment appointment = appointmentManager.findById(appointmentId);
        if (appointment == null || appointment.getStatistics() == null) {
            return ResponseEntity.badRequest().body("No statistics found for this appointment.");
        }

        List<PerformanceDto> result = appointment.getStatistics().getPerformances().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    public ResponseEntity<?> deletePerformancesByAppointmentId(Long appointmentId) {
        Appointment appointment = appointmentManager.findById(appointmentId);

        if (appointment == null || appointment.getStatistics() == null) {
            return ResponseEntity.badRequest().body("Statistics not found.");
        }

        Statistics statistics = appointment.getStatistics();

        performanceRepository.deleteAll(statistics.getPerformances());
        statistics.setPerformances(null);
        statisticsRepository.save(statistics);

        return ResponseEntity.ok("Performances deleted successfully.");
    }

    // Helper methods
    private ExercisePerformance mapToEntity(PerformanceDto dto, Statistics statistics) {
        ExercisePerformance performance = new ExercisePerformance();
        performance.setExercise(dto.getExercise());
        performance.setReps(dto.getReps());
        performance.setSets(dto.getSets());
        performance.setWeight(dto.getWeight());
        performance.setDuration(dto.getDuration());
        performance.setRestTime(dto.getRestTime());
        performance.setStatistics(statistics);
        return performance;
    }

    private PerformanceDto mapToDto(ExercisePerformance entity) {
        PerformanceDto dto = new PerformanceDto();
        dto.setExercise(entity.getExercise());
        dto.setReps(entity.getReps());
        dto.setSets(entity.getSets());
        dto.setWeight(entity.getWeight());
        dto.setDuration(entity.getDuration());
        dto.setRestTime(entity.getRestTime());
        return dto;
    }
}
