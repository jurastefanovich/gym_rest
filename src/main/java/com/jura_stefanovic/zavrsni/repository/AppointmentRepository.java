package com.jura_stefanovic.zavrsni.repository;

import com.jura_stefanovic.zavrsni.constants.Status;
import com.jura_stefanovic.zavrsni.model.entity.Appointment;
import com.jura_stefanovic.zavrsni.model.entity.GymService;
import com.jura_stefanovic.zavrsni.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
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

    @Query("Select a from Appointment a where a.status = ?1")
    List<Appointment> findByStatus(Status status);

    @Query("SELECT a FROM Appointment a LEFT JOIN FETCH a.users WHERE a.status = :status")
    List<Appointment> findByStatusWithUsers(@Param("status") Status status);

    @Query("SELECT a FROM Appointment a JOIN a.users u WHERE u.id = :userId ORDER BY a.date ASC")
    List<Appointment> findByUserLimit(@Param("userId") Long userId);

    @Query("""
    SELECT a FROM Appointment a 
    JOIN a.users u
    WHERE u.id = :userId
      AND a.status = 'ACTIVE'
      AND a.date BETWEEN :startOfWeek AND :endOfWeek
    ORDER BY a.date ASC
""")
    List<Appointment> findActiveSessionsForUserThisWeek(@Param("userId") Long userId,
                                                        @Param("startOfWeek") LocalDateTime startOfWeek,
                                                        @Param("endOfWeek") LocalDateTime endOfWeek);

    @Query("""
    SELECT COUNT(a)
    FROM Appointment a join a.users u where u.id = ?3
    and a.status IN (?1, ?2)
    """)
    Long countTotalSessions(Status finished, Status active, Long userId);

    @Query("""
    SELECT a.date as originalDate
    FROM Appointment a
    JOIN a.users u
    WHERE u.id = :userId
      AND a.status = 'FINISHED'
    ORDER BY a.date DESC
""")
    List<LocalDateTime> findFinishedAppointmentDatesByUser(@Param("userId") Long userId);

    @Query("SELECT a FROM Appointment a left join a.users u where u.id = :id " +
            "and a.status = :status AND a.date >= :from " +
            "AND a.statistics IS NOT NULL")
    List<Appointment> findFinishedAppointmentsWithStatisticsAfter(
            @Param("status") Status status,
            @Param("from") LocalDateTime from,
            @Param("id") Long id
    );
    @Query("""
    SELECT COUNT(a)
    FROM Appointment a
    WHERE a.status = 'ACTIVE'
      AND a.date BETWEEN :startOfDay AND :endOfDay
""")
    Integer getNumberOfTodaysSessions(
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );

    @Query("""
    SELECT FUNCTION('to_char', a.date, 'Day') AS day,
           COUNT(a) AS count
    FROM Appointment a
    WHERE a.status = 'ACTIVE'
      AND a.date >= :startOfWeek
      AND a.date <= :endOfWeek
    GROUP BY FUNCTION('to_char', a.date, 'Day')
    ORDER BY MIN(a.date)
""")
    List<Object[]> findWeeklyAttendance(
            @Param("startOfWeek") LocalDateTime startOfWeek,
            @Param("endOfWeek") LocalDateTime endOfWeek
    );

}
