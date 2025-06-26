package com.jura_stefanovic.zavrsni.service;

import com.jura_stefanovic.zavrsni.dto.models.gym.GymServiceDetailsDto;
import com.jura_stefanovic.zavrsni.dto.models.gym.GymServiceDto;
import com.jura_stefanovic.zavrsni.dto.requests.GymServiceRequest;
import com.jura_stefanovic.zavrsni.dto.response.ErrorResponse;
import com.jura_stefanovic.zavrsni.manager.GymServiceManger;
import com.jura_stefanovic.zavrsni.model.entity.Appointment;
import com.jura_stefanovic.zavrsni.model.entity.GymService;
import com.jura_stefanovic.zavrsni.model.entity.User;
import com.jura_stefanovic.zavrsni.manager.UserManager;
import com.jura_stefanovic.zavrsni.repository.GymServiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class GymServiceService {

    private final GymServiceRepository gymServiceRepository;
    private final GymServiceManger gymServiceManger;
    private final UserManager userManager;

    public List<GymServiceDto> getAll() {
        return gymServiceManger.findAll().stream().map(GymServiceDto::new).toList();
    }

    public ResponseEntity<?> getById(Long id) {
        GymService gymService = gymServiceManger.findById(id).orElse(null);
        if (gymService == null) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Service not found"));
        }
        List<String> exercies = gymService.getExercises().stream().map(exercise -> gymServiceManger.formatEnumName(exercise.name())).toList();
        GymServiceDetailsDto dto = new GymServiceDetailsDto(gymService, exercies);
        return ResponseEntity.ok().body(dto);
    }

    public ResponseEntity<?> create(GymServiceRequest request) {

        GymService dbService = gymServiceManger.findByTitle(request.getTitle().toLowerCase());
        if (dbService != null) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Service already exists with id: " + dbService.getId()));
        }
        GymService gymService = new GymService(request);

        if (request.getTrainerId() != null) {
            User trainer = userManager.findById(request.getTrainerId());
            if (trainer == null) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Trainer not found"));
            }
            gymService.setTrainer(trainer);
        } else {
            gymService.setTrainer(null);
        }

        gymServiceManger.save(gymService);
        return ResponseEntity.ok("GymResponseDto service created successfully");
    }

    public ResponseEntity<?> update(Long id, GymServiceRequest request) {
        var existing = gymServiceManger.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorResponse("GymResponseDto service not found"));
        }

        GymService gymService = existing.get();
        gymService.setTitle(request.getTitle());
        gymService.setDescription(request.getDescription());
        gymService.setIndividual(request.isIndividual());
        if (request.isIndividual()) {
            gymService.setMaxUsersPerGroupSession(1);
            gymService.setDurationSeconds(null);
        } else {
            gymService.setMaxUsersPerGroupSession(request.getMaxUsersPerGroupSession());
            gymService.setDurationSeconds(request.getDurationSeconds());
        }
        gymService.setNeedsTrainer(request.isTrainerRequired());



        if (request.getTrainerId() != null) {
            User trainer = userManager.findById(request.getTrainerId());
            if (trainer == null) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Trainer not found"));
            }
            gymService.setTrainer(trainer);
        } else {
            gymService.setTrainer(null);
        }

        gymServiceManger.save(gymService);
        return ResponseEntity.ok("GymResponseDto service updated successfully");
    }

    public ResponseEntity<?> delete(Long id) {
        if (!gymServiceManger.existsById(id)) {
            return ResponseEntity.badRequest().body("GymResponseDto service not found");
        }
        GymService db = gymServiceManger.findById(id).orElse(null);
        if (db == null) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Service wasn't found"));
        }

        db.setActive(false);
        gymServiceManger.save(db);

        return ResponseEntity.ok("GymResponseDto service deleted successfully");
    }

    public ResponseEntity<?> getGroupServices() {
        List<GymService> all = gymServiceManger.findAllGroup();
        if (all != null && !all.isEmpty()) {
            return ResponseEntity.ok().body(all.stream().map(GymServiceDto::new).toList());
        }
        return ResponseEntity.ok().body(new ArrayList<>());
    }

    public ResponseEntity<?> getAllExercises() {
        return ResponseEntity.ok().body(gymServiceManger.getAllExercises());
    }
}
