package com.jura_stefanovic.zavrsni.utils;

import com.jura_stefanovic.zavrsni.constants.Exercise;
import com.jura_stefanovic.zavrsni.constants.Status;
import com.jura_stefanovic.zavrsni.manager.AppointmentManager;
import com.jura_stefanovic.zavrsni.manager.StatisticsManager;
import com.jura_stefanovic.zavrsni.model.entity.Appointment;
import com.jura_stefanovic.zavrsni.model.entity.ExercisePerformance;
import com.jura_stefanovic.zavrsni.model.entity.Statistics;
import com.jura_stefanovic.zavrsni.model.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@AllArgsConstructor
public class StatisticsGenerator {
    private final AppointmentManager appointmentManager;
    private final StatisticsManager statisticsManager;
    public void generateStatisticsForAllFinishedAppointmentsWithUsers() {
        List<Appointment> finishedAppointments = appointmentManager.findByStatusWithUsers(Status.FINISHED);

        if (finishedAppointments.isEmpty()) {
            return ;
        }

        for (Appointment appointment : finishedAppointments) {
            // Skip if statistics already exists
            if (appointment.getStatistics() != null) continue;

            Statistics stats = new Statistics();
            stats.setAppointment(appointment);

            List<ExercisePerformance> allPerformances = new ArrayList<>();

            List<User> usersInAppointment = appointment.getUsers();
            if (usersInAppointment == null || usersInAppointment.isEmpty()) continue;

            for (User user : usersInAppointment) {
                for (Exercise exercise : getRandomExercises()) {
                    ExercisePerformance perf = new ExercisePerformance();
                    perf.setExercise(exercise);
                    perf.setSets(randomBetween(2, 5));
                    perf.setReps(randomBetween(6, 15));
                    perf.setWeight(randomBetween(20, 100));
                    perf.setDuration(randomBetween(30, 180));     // seconds
                    perf.setRestTime(randomBetween(30, 90));      // seconds
                    perf.setStatistics(stats);
                    perf.setUser(user);

                    allPerformances.add(perf);
                }
            }

            stats.setPerformances(allPerformances);
            statisticsManager.save(stats);

            appointment.setStatistics(stats);
            appointmentManager.save(appointment);
        }
    }

    private int randomBetween(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    private List<Exercise> getRandomExercises() {
        // Choose 5–10 random exercises per user
        List<Exercise> all = Arrays.asList(Exercise.values());
        Collections.shuffle(all);
        return all.subList(0, randomBetween(5, Math.min(10, all.size())));
    }


}
