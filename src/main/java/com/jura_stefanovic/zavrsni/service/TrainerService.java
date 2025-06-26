package com.jura_stefanovic.zavrsni.service;

import com.jura_stefanovic.zavrsni.constants.Role;
import com.jura_stefanovic.zavrsni.dto.models.trainer.TrainerDto;
import com.jura_stefanovic.zavrsni.dto.requests.EmployeeScheduleRequest;
import com.jura_stefanovic.zavrsni.dto.requests.TrainerRequest;
import com.jura_stefanovic.zavrsni.manager.UserManager;
import com.jura_stefanovic.zavrsni.messages.ErrorMessages;
import com.jura_stefanovic.zavrsni.model.entity.Schedule;
import com.jura_stefanovic.zavrsni.model.entity.User;
import com.jura_stefanovic.zavrsni.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainerService {

    private final UserManager userManager;
    private final PasswordEncoder passwordEncoder;
    private final ScheduleRepository scheduleRepository;

    public TrainerService(UserManager userManager, PasswordEncoder passwordEncoder, ScheduleRepository scheduleRepository) {
        this.userManager = userManager;
        this.passwordEncoder = passwordEncoder;
        this.scheduleRepository = scheduleRepository;
    }

    // Create
    public ResponseEntity<?> addTrainer(TrainerRequest request) {

        try {
            trainerAuth(request);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        User trainer = new User();
        trainer.setEmail(request.getEmail());
        trainer.setUsername(request.getUsername());
        trainer.setSpecialisation(request.getSpecs());
        trainer.setDescription(request.getDesc());
        trainer.setFirstName(request.getFirstName());
        trainer.setLastName(request.getLastName());
        trainer.setPassword(passwordEncoder.encode(request.getPassword()));
        trainer.setRole(Role.TRAINER);

        userManager.save(trainer);

        return ResponseEntity.ok().body("Trainer created successfully");
    }

    private void trainerAuth(TrainerRequest request) throws Exception {
        if (userManager.findByUsername(request.getUsername()).isPresent()) {
            throw new Exception("Username taken");
        } else if (userManager.findByEmail(request.getEmail()) != null) {
            throw new Exception("Email taken");
        }
    }
    // Read all trainers
    public List<User> getAllTrainers() {
        return userManager.findAllByRole(Role.TRAINER);
    }

    // Read trainer by id
    public ResponseEntity<?> getTrainerById(Long id) {
        User trainer = userManager.findById(id);
        if (trainer != null && trainer.getRole() == Role.TRAINER) {
            return ResponseEntity.ok().body(trainer);
        }
        return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
    }

    // Update trainer
    public ResponseEntity<?> updateTrainer(Long id, TrainerRequest request) {
        User existingTrainerOpt = userManager.findById(id);
        if (existingTrainerOpt == null || existingTrainerOpt.getRole() != Role.TRAINER) {
            return ResponseEntity.badRequest().body("Trainer not found");
        }
        User existingTrainer = existingTrainerOpt;

        existingTrainer.setEmail(request.getEmail());
        existingTrainer.setUsername(request.getUsername());

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            existingTrainer.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        userManager.save(existingTrainer);
        return ResponseEntity.ok().body("Trainer updated successfully");
    }

    // Delete trainer
    public ResponseEntity<?> deleteTrainer(Long id) {
        User existingTrainerOpt = userManager.findById(id);
        if (existingTrainerOpt == null || existingTrainerOpt.getRole() != Role.TRAINER) {
            return ResponseEntity.badRequest().body( "Trainer not found");
        }

        userManager.deleteById(id);
        return ResponseEntity.ok().body( "Trainer deleted successfully");
    }

    public ResponseEntity<?> addSchedulesToTrainer(Long trainerId, List<EmployeeScheduleRequest> schedules) {
        User trainer = userManager.findById(trainerId);
        if (trainer == null || trainer.getRole() != Role.TRAINER) {
            return ResponseEntity.badRequest().body("Trainer not found");
        }

        List<Schedule> employeeSchedules = new ArrayList<>();
        for (EmployeeScheduleRequest req : schedules) {
            Schedule schedule = new Schedule();
            schedule.setDay(req.getDay());
            schedule.setStartTime(req.getStart());
            schedule.setEndTime(req.getEnd());
            schedule.setTrainer(trainer);
            employeeSchedules.add(schedule);
        }

        scheduleRepository.saveAll(employeeSchedules);

        return ResponseEntity.ok().body("Schedules added successfully");
    }

    public ResponseEntity<?> getIntroductionById(Long id) {
        User trainer = userManager.findById(id);
        if (trainer != null && trainer.getRole() == Role.TRAINER) {
            return ResponseEntity.ok().body(new TrainerDto(trainer));
        }
        return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> getAll() {
        List<User> users = getAllTrainers();
        if (users != null && !users.isEmpty()) {
            List<TrainerDto> dto = users.stream().map(TrainerDto::new).toList();
            return ResponseEntity.ok().body(dto);
        }
        return ResponseEntity.ok().body(new ArrayList<>());
    }
}
