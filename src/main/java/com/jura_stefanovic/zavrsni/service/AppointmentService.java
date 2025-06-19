package com.jura_stefanovic.zavrsni.service;

import com.jura_stefanovic.zavrsni.constants.Exercise;
import com.jura_stefanovic.zavrsni.constants.Status;
import com.jura_stefanovic.zavrsni.dto.models.ExerciseDataDTO;
import com.jura_stefanovic.zavrsni.dto.models.ExerciseDefaultsDTO;
import com.jura_stefanovic.zavrsni.dto.models.appointment.*;
import com.jura_stefanovic.zavrsni.dto.models.gym.GymServiceDetailsDto;
import com.jura_stefanovic.zavrsni.dto.models.users.ParticipantDto;
import com.jura_stefanovic.zavrsni.dto.requests.FinishAppointmentRequestDTO;
import com.jura_stefanovic.zavrsni.dto.requests.GroupAppointmentRequest;
import com.jura_stefanovic.zavrsni.dto.requests.ServiceAppointmentDto;
import com.jura_stefanovic.zavrsni.dto.response.ErrorResponse;
import com.jura_stefanovic.zavrsni.manager.AppointmentManager;
import com.jura_stefanovic.zavrsni.manager.GymServiceManger;
import com.jura_stefanovic.zavrsni.manager.StatisticsManager;
import com.jura_stefanovic.zavrsni.manager.UserManager;
import com.jura_stefanovic.zavrsni.model.entity.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AppointmentService {
    private final AppointmentManager appointmentManager;
    private final GymServiceManger gymServiceManger;
    private final UserManager userManager;
    private final StatisticsManager statisticsManager;
    //Old method currently unused
    public ResponseEntity<?> saveAppointment(ServiceAppointmentDto dto) {
        User loggedInUser = userManager.getCurrentUser();

        try {
            GymService gymService = gymServiceManger.findById(dto.getServiceId()).orElse(null);

            if (gymService == null) {
                return ResponseEntity.badRequest().body("No service found, can't book appointment");
            }

            // Step 1: Check if an appointment exists at that date with the same service
            Optional<Appointment> existingAppointmentOpt =
                    appointmentManager.findByServiceAndDate(gymService, dto.getDateAndTime());

            if (existingAppointmentOpt.isPresent()) {
                Appointment existingAppointment = existingAppointmentOpt.get();
                List<User> users = existingAppointment.getUsers();

                // Step 2: Check if user is already added
                if (users.contains(loggedInUser)) {
                    return ResponseEntity.ok("User already in the appointment.");
                }

                // Step 3: Check if there's room
                if (users.size() >= gymService.getMaxUsersPerGroupSession()) {
                    return ResponseEntity.badRequest().body("Appointment is full.");
                }

                // Step 4: Add user
                users.add(loggedInUser);
                existingAppointment.setUsers(users);
                appointmentManager.save(existingAppointment);

                return ResponseEntity.ok("User added to existing appointment.");
            }

            // Step 5: No existing appointment → create a new one
            Appointment appointment = new Appointment();
            List<User> users = new ArrayList<>();
            users.add(loggedInUser);
            appointment.setUsers(users);
            appointment.setDate(dto.getDateAndTime());
            appointment.setTrainer(gymService.getTrainer());
            appointment.setStatus(Status.PENDING);
            appointment.setService(gymService);
            appointment.setIndividual(gymService.isIndividual());

            appointmentManager.save(appointment);

            return ResponseEntity.ok("Appointment created.");
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse("Error saving appointment: " + e.getMessage()));
        }
    }


    public ResponseEntity<?> getAllAppointments() {
        User currentUser = userManager.getCurrentUser();
        if (!currentUser.isUser()) {
            List<Appointment> list = appointmentManager.findAll();
            List<AppointmentListDto> dto = list.stream().map(AppointmentListDto::new).toList();
            return ResponseEntity.ok().body(dto);
        } else {
            List<Appointment> list = appointmentManager.findAppointmentsByUserId(currentUser.getId());
            List<AppointmentListDto> dto = list.stream().map(AppointmentListDto::new).toList();
            return ResponseEntity.ok().body(dto);
        }
    }

    public ResponseEntity<?> getAppointmentById(Long id) {
        User loggedInUser = userManager.getCurrentUser();
        Appointment appointment = appointmentManager.findById(id);

        if (appointment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Appointment not found"));
        }

        boolean isIncluded = false;
        List<User> users = appointment.getUsers();
        if (users != null && !users.isEmpty()) {
            isIncluded = users.stream().anyMatch(user -> user.getId().equals(loggedInUser.getId()));
        }

        AppointmentDetailsDto dto = new AppointmentDetailsDto(appointment, isIncluded);
        return ResponseEntity.ok(dto);
    }


    public ResponseEntity<?> updateAppointment(Long id, AppointmentPutDto updated) {
        try {
            Optional<Appointment> existing = Optional.ofNullable(appointmentManager.findById(id));

            if (existing.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Appointment dbAppointment = existing.get();

            try {
                checkUpdateData(updated, dbAppointment.getId());
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
            }

            GymService gymService = gymServiceManger.findById(updated.getServiceId()).orElse(null);
            User trainer = userManager.findById(updated.getTrainerId());

            dbAppointment.setService(gymService);
            dbAppointment.setTrainer(trainer);
            dbAppointment.setDescription(updated.getNotes());
            dbAppointment.setDate(updated.getDateTime());

            Appointment saved = appointmentManager.save(dbAppointment);

            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating appointment: " + e.getMessage());
        }
    }

    private void checkUpdateData(AppointmentPutDto update, Long dbAppId) throws Exception {
        GymService gymService = gymServiceManger.findById(update.getServiceId()).orElse(null);
        User trainer = userManager.findById(update.getTrainerId());
        Optional<Appointment> dbApp = appointmentManager.findByDate(update.getDateTime());

        if (gymService == null) {
            throw new Exception("Gym service doesn't exist.");
        } else if (trainer == null) {
            throw new Exception("Trainer doesn't exist");
        } else if (dbApp.isPresent()) {
            if (!dbApp.get().getId().equals(dbAppId)) {
                throw new Exception("Appointment already exists at this time");
            }
        }
    }

    public void deleteAppointment(Long id) {
        appointmentManager.delete(id);
    }

    public ResponseEntity<?> getAppointmentsByTrainerId(Long trainerId) {
        List<Appointment> appointments = appointmentManager.findByTrainerId(trainerId);
        return ResponseEntity.ok(appointments);
    }

    public ResponseEntity<?> getAppointmentsBetweenDates(LocalDateTime start, LocalDateTime end) {
        List<Appointment> appointments = appointmentManager.findBetweenDates(start, end);
        return ResponseEntity.ok(appointments);
    }

    public ResponseEntity<?> createAppointmentForTrainer(Appointment appointment, Long trainerId) {
        try {
            User user = userManager.findById(trainerId);
            if (user.isTrainer()) {
                Appointment saved = appointmentManager.save(appointment);
                return ResponseEntity.ok(saved);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating trainer appointment: " + e.getMessage());
        }
        return ResponseEntity.badRequest().body("Selected user isn't a trainer");
    }


    public ResponseEntity<?> getOverviewDetailsForBookingAppointment(Long trainerId, Long serviceId) {
        User trainer = userManager.findById(trainerId);
        GymService service = gymServiceManger.findById(serviceId).orElse(null);

        if (trainer == null || service == null) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Failed to load data"));
        }

        GymServiceDetailsDto gymServiceDetailsDto = new GymServiceDetailsDto(service, trainer);

        return ResponseEntity.ok().body(gymServiceDetailsDto);
    }

    public ResponseEntity<?> cancelAppointment(Long id) {
        Appointment appointment = appointmentManager.findById(id);
        if (appointment == null) {
            return ResponseEntity.badRequest().body("Failed to cancel appointment, no appointment was found");
        } else if (appointment.isDone()) {
            return ResponseEntity.badRequest().body("Can't delete an appointment that is finished");
        }
        appointment.setStatus(Status.CANCELLED);
        appointmentManager.delete(appointment.getId());
        return ResponseEntity.ok().body(HttpStatus.OK);
    }

    public ResponseEntity<?> getGroupAppointments() {
        List<Appointment> appointments = appointmentManager.findAllGroupAppointments();
        User loggedInUser = userManager.getCurrentUser();
        if (loggedInUser.isUser()) {
            return ResponseEntity.ok().body(new ArrayList<>());
        } else if (loggedInUser.isTrainer()) {
            appointments = appointmentManager.findAllGroupByUserId(loggedInUser.getId());
        }
        if (appointments != null && !appointments.isEmpty()) {
            List<GroupAppointmentDto> dto = appointments.stream().map(GroupAppointmentDto::new).toList();
            return ResponseEntity.ok().body(dto);
        }
        return ResponseEntity.ok().body(new ArrayList<>());
    }

    public ResponseEntity<?> getActiveAppointmentsForService(Long serviceId) {
        GymService gymService = gymServiceManger.findById(serviceId).orElse(null);
        if (gymService == null) {
            return ResponseEntity.ok().body(new ArrayList<>());
        }

        List<Appointment> appointments = appointmentManager.findCurrentForService(serviceId);
        List<AppointmentInServiceDto> dtos = appointments.stream().map(AppointmentInServiceDto::new).toList();

        return ResponseEntity.ok().body(dtos);
    }

    public ResponseEntity<?> createGroupAppointment(GroupAppointmentRequest dto) {

        try {
            missingInfoGroupAppointment(dto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }

        GymService dbService = gymServiceManger.findById(dto.getServiceId()).orElse(null);
        if (dbService == null) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Selected service doesn't exit"));
        }
        Appointment dbAppointment = appointmentManager.findByDate(dto.getDateTime()).orElse(null);

        if (dbAppointment != null) {
            return ResponseEntity.badRequest().body(new ErrorResponse("An appointment is already booked for this time"));
        } else if (dbService.isIndividual()) {
            return ResponseEntity.badRequest().body(new ErrorResponse("The selected service doesn't support group training"));
        }

        User trainer = userManager.findById(dto.getTrainerId());
        if (trainer == null) {
            return ResponseEntity.badRequest().body(new ErrorResponse("The selected trainer does not exist or isn't currently active"));
        } else if (!trainer.isTrainer()) {
            return ResponseEntity.badRequest().body(new ErrorResponse("The selected user is not a trainer"));
        }

        Appointment appointment = new Appointment();
        appointment.setIndividual(false);
        appointment.setService(dbService);
        appointment.setDate(dto.getDateTime());
        appointment.setStatus(Status.ACTIVE);
        appointment.setTrainer(trainer);

        if (dto.getDescription() != null) {
            appointment.setDescription(dto.getDescription());
        }

        appointmentManager.save(appointment);
        return ResponseEntity.ok().body("Appointment created!");
    }

    private void missingInfoGroupAppointment(GroupAppointmentRequest request) {
        List<String> missingFields = new ArrayList<>();

        if (request.getServiceId() == null) {
            missingFields.add("Service");
        }

        if (request.getDateTime() == null) {
            missingFields.add("Date and Time");
        } else if (request.getDateTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("dateTime cannot be in the past");
        }

        if (request.getTrainerId() == null) {
            missingFields.add("Trainer");
        }

        if (!missingFields.isEmpty()) {
            throw new IllegalArgumentException(
                    "Missing required fields: " + String.join(", ", missingFields)
            );
        }
    }

    public ResponseEntity<?> joinSession(Long appointmentId) {
        Appointment appointment = appointmentManager.findById(appointmentId);
        if (appointment == null) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Selected appointment wasn't found"));
        }
        User loggedInUser = userManager.getCurrentUser();
        GymService gymService = appointment.getService();

        if (appointment.getNumberOfUsers() >= gymService.getMaxUsersPerGroupSession()) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Session is completely booked"));
        }

        if (appointment.getUsers() != null && appointment.getUsers().contains(loggedInUser)) {
            return ResponseEntity.badRequest().body(new ErrorResponse("User already joined this session"));
        }

        appointment.addUser(loggedInUser);
        appointmentManager.save(appointment);

        return ResponseEntity.ok("Successfully joined the session");
    }


    public ResponseEntity<?> removeFromSession(Long appointmentId) {
        Appointment appointment = appointmentManager.findById(appointmentId);
        if (appointment == null) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Selected appointment wasn't found"));
        }

        User loggedInUser = userManager.getCurrentUser();
        if (appointment.getUsers() == null || !appointment.getUsers().stream().anyMatch(user -> user.getId().equals(loggedInUser.getId()))) {
            return ResponseEntity.badRequest().body(new ErrorResponse("User is not part of this session"));
        }

        appointment.getUsers().removeIf(user -> user.getId().equals(loggedInUser.getId()));
        appointmentManager.save(appointment);

        return ResponseEntity.ok("Successfully removed from the session");
    }

    public ResponseEntity<?> getAllForTrainer() {
        User currentUser = userManager.getCurrentUser();
        if (!currentUser.isTrainer()) {
            return ResponseEntity.ok().body(new ArrayList<>());
        } else {
            List<Appointment> list = appointmentManager.findAllForTrainer(currentUser.getId());
            List<AppointmentListDto> dto = list.stream().map(AppointmentListDto::new).toList();
            return ResponseEntity.ok().body(dto);
        }
    }

    public ResponseEntity<?> getAllParticipants(Long id) {
        Appointment appointment = appointmentManager.findById(id);
        if (appointment == null) {
            return ResponseEntity.badRequest().body("Appointment could not be found");
        }
        List<User> users = appointment.getUsers();
        if (users != null || !users.isEmpty()) {
            return ResponseEntity.ok().body(users.stream().map(ParticipantDto::new).toList());
        }
        return ResponseEntity.ok().body(new ArrayList<>());
    }

    public ResponseEntity<?> finishAppointment(FinishAppointmentRequestDTO requestDTO, Long appointmentId) {
        Appointment dbAppointment = appointmentManager.findById(appointmentId);

        dbAppointment.setStatus(Status.FINISHED);

        try {
            setStatsForAppointment(requestDTO, dbAppointment);
            setStatisticsForUsers(requestDTO, dbAppointment);
            appointmentManager.save(dbAppointment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }

        return ResponseEntity.ok().body("Appointment finished successfully");
    }

    private void setStatisticsForUsers(FinishAppointmentRequestDTO requestDTO, Appointment dbAppointment) {
        Map<Long, Map<String, ExerciseDataDTO>> userData = requestDTO.getUserExerciseData();
        Statistics stats = new Statistics();

        List<ExercisePerformance> allPerformances = new ArrayList<>();

        for (Map.Entry<Long, Map<String, ExerciseDataDTO>> userEntry : userData.entrySet()) {
            Long userId = userEntry.getKey();
            Map<String, ExerciseDataDTO> exercises = userEntry.getValue();

            User user = userManager.findById(userId);
            if (user == null) {
                throw new IllegalArgumentException("User with ID " + userId + " not found");
            }

            for (Map.Entry<String, ExerciseDataDTO> exerciseEntry : exercises.entrySet()) {
                String exerciseName = exerciseEntry.getKey();
                ExerciseDataDTO dto = exerciseEntry.getValue();

                ExercisePerformance performance = new ExercisePerformance();
                performance.setExercise(Exercise.valueOf(exerciseName));
                performance.setReps(dto.getReps());
                performance.setSets(dto.getSets());
                performance.setWeight(dto.getWeight());
                performance.setDuration(dto.getDuration());
                performance.setRestTime(dto.getRestTime());
                performance.setStatistics(stats);
                performance.setUser(user); // Safe — but don’t reassign list in User

                allPerformances.add(performance);
            }
        }

        stats.setPerformances(allPerformances);
        statisticsManager.save(stats);
    }


    private void setStatsForAppointment(FinishAppointmentRequestDTO requestDTO, Appointment dbAppointment) {
        Statistics stats = new Statistics();
        stats.setAppointment(dbAppointment);

        Map<String, ExerciseDefaultsDTO> defaults = requestDTO.getExerciseDefaults();

        List<ExercisePerformance> performances = new ArrayList<>();

        for (Map.Entry<String, ExerciseDefaultsDTO> entry : defaults.entrySet()) {
            String exerciseName = entry.getKey();
            ExerciseDefaultsDTO dto = entry.getValue();
            ExercisePerformance performance = new ExercisePerformance();
            performance.setExercise(Exercise.valueOf(exerciseName));
            performance.setReps(dto.getReps());
            performance.setSets(dto.getSets());
            performance.setWeight(dto.getWeight());
            performance.setDuration(dto.getDuration());
            performance.setRestTime(dto.getRestTime());
            performance.setStatistics(stats);
            performances.add(performance);
        }
        stats.setPerformances(performances);
        Statistics saved = statisticsManager.save(stats);
        dbAppointment.setStatistics(saved);

    }
}
