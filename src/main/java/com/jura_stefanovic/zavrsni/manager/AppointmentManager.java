package com.jura_stefanovic.zavrsni.manager;

import com.jura_stefanovic.zavrsni.constants.Status;
import com.jura_stefanovic.zavrsni.model.entity.GymService;
import com.jura_stefanovic.zavrsni.repository.AppointmentRepository;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;
import com.jura_stefanovic.zavrsni.model.entity.Appointment;
import jakarta.persistence.EntityNotFoundException;

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
}
