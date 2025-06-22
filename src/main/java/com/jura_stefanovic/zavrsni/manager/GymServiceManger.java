package com.jura_stefanovic.zavrsni.manager;

import com.jura_stefanovic.zavrsni.constants.Exercise;
import com.jura_stefanovic.zavrsni.model.entity.GymService;
import com.jura_stefanovic.zavrsni.repository.GymServiceRepository;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class GymServiceManger {
    private final GymServiceRepository gymServiceRepository;

    public GymServiceManger(GymServiceRepository gymServiceRepository) {
        this.gymServiceRepository = gymServiceRepository;
    }

    public Optional<GymService> findById(Long id) {
        return gymServiceRepository.findById(id);
    }

    public GymService save(GymService gymService) {
        return gymServiceRepository.save(gymService);
    }

    public List<GymService> findAll() {
        return gymServiceRepository.findAll();
    }

    public List<String> getAllExercises() {
        return Arrays.stream(Exercise.values())
                .map(exercise -> formatEnumName(exercise.name()))
                .sorted()
                .collect(Collectors.toList());
    }

    public String formatEnumName(String name) {
        return Arrays.stream(name.split("_"))
                .map(part -> part.charAt(0) + part.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }

    public List<GymService> findAllGroup() {
        return gymServiceRepository.findAllGroup();
    }

    public GymService findByTitle(String lowerCase) {
        return gymServiceRepository.findByTitle(lowerCase);
    }

    public boolean existsById(Long id) {
        return gymServiceRepository.existsById(id);
    }
}
