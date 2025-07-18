package com.jura_stefanovic.zavrsni.service;

import com.jura_stefanovic.zavrsni.constants.Exercise;
import com.jura_stefanovic.zavrsni.constants.Status;
import com.jura_stefanovic.zavrsni.dto.models.appointment.AppointmentDetailsDto;
import com.jura_stefanovic.zavrsni.dto.response.ExerciseChartDTO;
import com.jura_stefanovic.zavrsni.dto.response.ExerciseRepsSummaryDTO;
import com.jura_stefanovic.zavrsni.manager.AppointmentManager;
import com.jura_stefanovic.zavrsni.manager.ExercisePerformanceManager;
import com.jura_stefanovic.zavrsni.manager.StatisticsManager;
import com.jura_stefanovic.zavrsni.manager.UserManager;
import com.jura_stefanovic.zavrsni.model.entity.Appointment;
import com.jura_stefanovic.zavrsni.model.entity.ExercisePerformance;
import com.jura_stefanovic.zavrsni.model.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.image.RescaleOp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StatisticsService {
    private final StatisticsManager statisticsManager;
    private final AppointmentManager appointmentManager;
    private final UserManager userManager;
    private final ExercisePerformanceManager exercisePerformanceManager;

    private User getUser() {
        return userManager.getCurrentUser();
    }

    public ResponseEntity<?> getLastAppointments(int numberOfAppointments) {
        User user = getUser();
        List<Appointment> appointments = appointmentManager.getLastAppointments(user.getId()).stream().limit(numberOfAppointments).toList();
        List<AppointmentDetailsDto> dto = appointments.stream().map(a -> new AppointmentDetailsDto(a, true)).toList();
        return ResponseEntity.ok().body(dto);
    }

    public ResponseEntity<?> getQuickStats() {
        User user = getUser();
        Map<String, String> statsMap = new HashMap<>();
        Long thisWeeksSessions = getSessionsForThisWeek(user.getId());
        String favoriteEx = getFavoriteExercise(user.getId());
        int sessionStreak = getSessionStreak(user.getId());
        Long numOfSessions = getNumberOfTotalSessions(user.getId());

        statsMap.put("Favorite Exercise", favoriteEx);
        statsMap.put("Session Streak", String.valueOf(sessionStreak));
        statsMap.put("Total Sessions", String.valueOf(numOfSessions));
        statsMap.put("Session this week", String.valueOf(thisWeeksSessions));

        return ResponseEntity.ok().body(statsMap);
    }

    private Long getSessionsForThisWeek(Long userId) {
        return (long) appointmentManager.findActiveSessionsForUserThisWeek(userId).size();
    }

    private String getFavoriteExercise(Long userId) {
        List<Exercise> exercises = exercisePerformanceManager.getFavoriteExercise(userId);
        if (exercises == null || exercises.isEmpty()) {
            return "N/A";
        }
        Exercise exercise = exercises.get(0);
        return exercise.name();
    }

    private int getSessionStreak(Long userId) {
        List<LocalDateTime> list = appointmentManager.findFinishedAppointmentDatesByUser(userId);
        return appointmentManager.calculateSessionStreakFromDateTimes(list);
    }

    private Long getNumberOfTotalSessions(Long userId) {
        return appointmentManager.countTotalSessions(userId);
    }

    public ResponseEntity<?> getBreakDown() {
        User user = getUser();
        List<ExerciseRepsSummaryDTO> dto = getTotalRepsPerExercise(user.getId());
        return ResponseEntity.ok().body(dto);
    }

    private List<ExerciseRepsSummaryDTO> getTotalRepsPerExercise(Long userId) {
        return exercisePerformanceManager.getTotalRepsPerExercise(userId).stream().limit(5)
                .map(obj -> new ExerciseRepsSummaryDTO(
                        (Exercise) obj[0],
                        (Long) obj[1]
                ))
                .collect(Collectors.toList());
    }

    public ResponseEntity<?> buildExerciseChart(String selectedExercise, String timeframe) {

        if (Objects.equals(selectedExercise, "")) {
            return ResponseEntity.ok().body(null);
        }
        User user = getUser();
        LocalDateTime from = switch (timeframe.toLowerCase()) {
            case "day" -> LocalDateTime.now().minusDays(1);
            case "week" -> LocalDateTime.now().minusWeeks(1);
            case "month" -> LocalDateTime.now().minusMonths(1);
            case "year" -> LocalDateTime.now().minusYears(1);
            default -> LocalDateTime.of(2000, 1, 1, 0, 0); //All time starting on 01/01/2000
        };

        // Get only finished appointments with statistics and performances
        List<Appointment> finishedAppointments = appointmentManager
                .findByStatusAndDateAfter(Status.FINISHED, from, user.getId());

        // Flatten all performances
        Map<LocalDate, Integer> dailyReps = new TreeMap<>();

        for (Appointment appointment : finishedAppointments) {
            if (appointment.getStatistics() == null) continue;

            LocalDate date = appointment.getDate().toLocalDate();

            for (ExercisePerformance performance : appointment.getStatistics().getPerformances()) {
                if (performance.getExercise() != Exercise.valueOf(normalizeExerciseString(selectedExercise))) continue;

                int reps = performance.getReps() != null ? performance.getReps() : 0;
                dailyReps.merge(date, reps, Integer::sum);
            }
        }

        List<String> labels = new ArrayList<>(dailyReps.keySet().stream()
                .map(LocalDate::toString).toList());

        List<Integer> values = new ArrayList<>(dailyReps.values());

        return ResponseEntity.ok().body( new ExerciseChartDTO(labels, values, selectedExercise, timeframe));
    }

    private String normalizeExerciseString(String val) {
        return val.trim().toUpperCase().replace(" ", "_");
    }

}
