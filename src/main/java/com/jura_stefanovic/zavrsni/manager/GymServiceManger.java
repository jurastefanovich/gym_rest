package com.jura_stefanovic.zavrsni.manager;

import com.jura_stefanovic.zavrsni.model.entity.GymService;
import com.jura_stefanovic.zavrsni.repository.GymServiceRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

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
}
