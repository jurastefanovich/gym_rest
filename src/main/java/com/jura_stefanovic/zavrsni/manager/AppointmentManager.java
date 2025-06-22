package com.jura_stefanovic.zavrsni.manager;

import com.jura_stefanovic.zavrsni.constants.Status;
import com.jura_stefanovic.zavrsni.model.entity.GymService;
import com.jura_stefanovic.zavrsni.repository.AppointmentRepository;
import org.springframework.stereotype.Component;
import com.jura_stefanovic.zavrsni.model.entity.Appointment;
import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class AppointmentManager {

    private final AppointmentRepository appointmentRepository;

    public AppointmentManager(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    // Create
    public Appointment save(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    // Read all
    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    // Read by ID
    public Appointment findById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment with ID " + id + " not found."));
    }

    // Update
    public Appointment update(Long id, Appointment updated) {
        Appointment existing = findById(id);
        existing.setDate(updated.getDate());
        existing.setStatus(updated.getStatus());
        existing.setUsers(updated.getUsers());
        existing.setTrainer(updated.getTrainer());
        return appointmentRepository.save(existing);
    }

    // Delete
    public void delete(Long id) {
        Appointment appointment = findById(id);
        appointmentRepository.delete(appointment);
    }

    // Find by trainer ID
    public List<Appointment> findByTrainerId(Long trainerId) {
        return appointmentRepository.findByTrainerId(trainerId);
    }

    // Find between dates
    public List<Appointment> findBetweenDates(LocalDateTime start, LocalDateTime end) {
        return appointmentRepository.findByDateBetween(start, end);
    }

    public List<Appointment> findAppointmentsByUserId(Long id) {
        return appointmentRepository.findAppointmentsByUserId(id);
    }

    public List<Appointment> findAllForTrainer(Long id) {
        return appointmentRepository.findAllForTrainer(id);
    }

    public List<Appointment> findByTrainerIdAndDateBetween(Long trainerId, LocalDateTime localDateTime, LocalDateTime localDateTime1) {
        return null;
    }

    public Optional<Appointment> findByServiceAndDate(GymService gymService, LocalDateTime dateAndTime) {
        return appointmentRepository.findByServiceAndDate(gymService, dateAndTime);
    }

    public Optional<Appointment> findByDate(LocalDateTime dateTime) {
        return appointmentRepository.findByDate(dateTime);
    }

    public Optional<Appointment> findByDateAndTrainer(LocalDateTime dateTime, Long trainerId) {
        return appointmentRepository.findByDateAndTrainer(dateTime, trainerId);
    }

    public List<Appointment> findAllGroupAppointments() {
        return appointmentRepository.findALlGroupAppointments();
    }

    public List<Appointment> findCurrentForService(Long serviceId) {
        return appointmentRepository.findCurrentForService(serviceId, Status.ACTIVE);
    }

    public List<Appointment> findAllGroupByUserId(Long id) {
        return appointmentRepository.findAllGroupByUserId(id);
    }

    public List<Appointment> findByStatus(Status status) {
        return appointmentRepository.findByStatus(status);
    }
    public List<Appointment> findByStatusWithUsers(Status status) {
        return appointmentRepository.findByStatusWithUsers(status);
    }

    public List<Appointment> getLastAppointments(Long id) {
        return appointmentRepository.findByUserLimit(id);
    }

    public List<Appointment> findActiveSessionsForUserThisWeek(Long userId) {
        return appointmentRepository.findActiveSessionsForUserThisWeek(userId, LocalDateTime.now(), LocalDateTime.now());
    }

    public Long countTotalSessions(Long userId) {
        return appointmentRepository.countTotalSessions(Status.FINISHED, Status.ACTIVE, userId);
    }

    public List<LocalDateTime> findFinishedAppointmentDatesByUser(Long userId) {
        return appointmentRepository.findFinishedAppointmentDatesByUser(userId);
    }

    public int calculateSessionStreakFromDateTimes(List<LocalDateTime> sessionDateTimes) {
        if (sessionDateTimes.isEmpty()) return 0;

        // Convert LocalDateTime to LocalDate
        List<LocalDate> sessionDates = sessionDateTimes.stream()
                .map(LocalDateTime::toLocalDate)
                .distinct()  // in case multiple sessions per day
                .sorted()
                .toList();

        int streak = 1;
        LocalDate previousDate = sessionDates.get(0);

        for (int i = 1; i < sessionDates.size(); i++) {
            LocalDate currentDate = sessionDates.get(i);
            if (currentDate.equals(previousDate.plusDays(1))) {
                streak++;
            } else if (!currentDate.equals(previousDate)) {
                break;
            }
            previousDate = currentDate;
        }

        return streak;
    }

    public List<Appointment> findByStatusAndDateAfter(Status status, LocalDateTime from, Long id) {
        return appointmentRepository.findFinishedAppointmentsWithStatisticsAfter(status, from, id);
    }
}
