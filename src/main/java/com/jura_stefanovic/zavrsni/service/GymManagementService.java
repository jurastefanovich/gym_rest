package com.jura_stefanovic.zavrsni.service;

import com.jura_stefanovic.zavrsni.constants.Day;
import com.jura_stefanovic.zavrsni.constants.Role;
import com.jura_stefanovic.zavrsni.dto.models.users.UserDto;
import com.jura_stefanovic.zavrsni.dto.response.GymResponseDto;
import com.jura_stefanovic.zavrsni.manager.AppointmentManager;
import com.jura_stefanovic.zavrsni.manager.GymManager;
import com.jura_stefanovic.zavrsni.manager.UserManager;
import com.jura_stefanovic.zavrsni.model.entity.Gym;
import com.jura_stefanovic.zavrsni.model.entity.Schedule;
import com.jura_stefanovic.zavrsni.model.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GymManagementService {
    private final GymManager gymManager;
    private final UserManager userManager;
    private final AppointmentManager appointmentManager;

    public ResponseEntity<?> getGymDataByView(String view) {
        User loggedInUser = userManager.getCurrentUser();
        if (!loggedInUser.isAdmin()) {
            return ResponseEntity.badRequest().body(HttpStatus.FORBIDDEN);
        }
        switch (view.toUpperCase()) {
            case "DASHBOARD":
                return getDashboard();
            case "GYM_INFO":
                return getGymInfo();
            case "MEMBERS":
                return getMembers();
            case "TRAINERS":
                return getTrainers();
            default:
                return ResponseEntity.badRequest().body("Invalid view parameter: " + view);
        }
    }


    public ResponseEntity<?> getDashboard() {
        return null;
    }

    public ResponseEntity<?> getGymInfo() {
        Optional<Gym> gymOpt = gymManager.find();
        if (!gymOpt.isPresent()) {
            return ResponseEntity.badRequest().body("GymResponseDto information not found");
        }
        GymResponseDto gym = new GymResponseDto(gymOpt.get());

        return ResponseEntity.ok().body(gym);
    }

    public ResponseEntity<?> getMembers() {
        return ResponseEntity.ok().body(getDtoByRole(Role.USER));
    }

    public ResponseEntity<?> getTrainers() {
        return ResponseEntity.ok().body(getDtoByRole(Role.TRAINER));
    }

    private List<UserDto> getDtoByRole(Role role) {
        List<User> users = userManager.findAllByRole(role);
        if (users != null && users.isEmpty()) {
            return new ArrayList<>();
        }
        return users.stream().map(UserDto::new).collect(Collectors.toList());
    }
    public List<String> getSpecializations() {
        return MASTER_SPECIALIZATIONS;
    }
    private final List<String> MASTER_SPECIALIZATIONS = List.of(
            "Strength Training",
            "Cardio Conditioning",
            "HIIT",
            "Yoga",
            "Pilates",
            "Functional Training",
            "Mobility & Flexibility",
            "Injury Prevention",
            "Martial Arts",
            "Endurance Training",
            "Agility Training",
            "Rehabilitation",
            "Sports Performance",
            "Bodyweight Training",
            "Kinesiology",
            "Biomechanics",
            "Physiotherapy",
            "Combat Conditioning",
            "Nutrition Coaching",
            "Mental Resilience"
    );


    public ResponseEntity<?> saveGym(GymResponseDto dto) {
        Optional<Gym> gymOpt = gymManager.find();
        if (!gymOpt.isPresent()) {
            return ResponseEntity.badRequest().body("Gym not found");
        }

        Gym dbGym = gymOpt.get();
        dbGym.updateGym(dto);

// clear existing schedules to trigger orphanRemoval
        dbGym.getWorkingHours().clear();

        for (Schedule dtoSchedule : dto.getWorkingHours()) {
            Schedule schedule = new Schedule();
            schedule.setDay(dtoSchedule.getDay());
            schedule.setWorking(dtoSchedule.isWorking());

            LocalDate today = LocalDate.now(); // or consistent date
            LocalTime start = dtoSchedule.getStartTime().toLocalTime();
            LocalTime end = dtoSchedule.getEndTime().toLocalTime();

            schedule.setStartTime(LocalDateTime.of(today, start));
            schedule.setEndTime(LocalDateTime.of(today, end));

            schedule.setTrainer(null);
            dbGym.getWorkingHours().add(schedule);
        }

        try {
            gymManager.save(dbGym);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok("Gym data saved!");
    }

    public ResponseEntity<?> getDashboardStats() {
        User loggedInUser = userManager.getCurrentUser();
        if (!loggedInUser.isAdmin()) {
            return ResponseEntity.badRequest().body(HttpStatus.FORBIDDEN);
        }
        Map<String, Integer> stats = new HashMap<>();
        Integer activeApp = appointmentManager.getNumberOfActiveAppointmentsToday();
        stats.put("active_appointments", activeApp);
        List<User> users = userManager.findAllByRole(Role.USER);
        stats.put("memebrs", users.size());
        Integer newThisMonth = userManager.getNewUserForThisMonth();
        stats.put("new_this_month", newThisMonth);
        return ResponseEntity.ok().body(stats);
    }

    public Map<String, Long> getWeeklyAttendance() {
        List<Object[]> results = appointmentManager.findWeeklyAttendance();

        return results.stream()
                .collect(Collectors.toMap(
                        row -> ((String) row[0]).trim(),
                        row -> (Long) row[1]
                ));
    }

    public Object getMonthlyUserRegistrations() {
        List<Object[]> results = userManager.findUserRegistrationsPerMonth();
        return results.stream()
                .collect(Collectors.toMap(
                        row -> (String) row[0],
                        row -> (Long) row[1]
                ));
    }
}
