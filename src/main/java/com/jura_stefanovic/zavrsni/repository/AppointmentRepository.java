package com.jura_stefanovic.zavrsni.repository;

import com.jura_stefanovic.zavrsni.constants.Status;
import com.jura_stefanovic.zavrsni.model.entity.Appointment;
import com.jura_stefanovic.zavrsni.model.entity.GymService;
import com.jura_stefanovic.zavrsni.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository  extends JpaRepository<Appointment, Long> {
    List<Appointment> findByTrainerId(Long trainerId);
    List<Appointment> findByDateBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT a FROM Appointment a JOIN a.users u WHERE u.id = :id")
    List<Appointment> findAppointmentsByUserId(@Param("id") Long id);

    @Query("Select a From Appointment a where a.trainer.id = ?1 order by a.date ASC")
    List<Appointment> findAllForTrainer(Long id);

    @Query("SELECT a FROM Appointment a WHERE a.service = ?1 AND a.date = ?2")
    Optional<Appointment> findByServiceAndDate(@Param("service") GymService service, @Param("date") LocalDateTime date);

    @Query("Select a from Appointment  a where a.individual = false order by a.date ASC")
    List<Appointment> findALlGroupAppointments();

    @Query("select a from Appointment a where a.date = ?1")
    Optional<Appointment> findByDate(LocalDateTime dateTime);
    @Query("SELECT a FROM Appointment a WHERE a.service.id = ?1 and a.service.active and a.status =?2 order by  a.date ASC")
    List<Appointment> findCurrentForService(Long serviceId, Status status);

    @Query("Select a from Appointment a where a.trainer.id = ?2 and a.date =?1 ")
    Optional<Appointment> findByDateAndTrainer(LocalDateTime dateTime, Long trainerId);

    @Query("Select a from Appointment  a where a.individual = false and a.trainer.id = ?1 order by a.date ASC")
    List<Appointment> findAllGroupByUserId(Long id);
}
