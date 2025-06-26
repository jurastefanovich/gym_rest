package com.jura_stefanovic.zavrsni.manager;

import com.jura_stefanovic.zavrsni.model.entity.Gym;
import com.jura_stefanovic.zavrsni.repository.GymRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GymManager {
    private final GymRepository gymRepository;

    public GymManager(GymRepository gymRepository) {
        this.gymRepository = gymRepository;
    }

    public Gym save(Gym gym) {
        return gymRepository.save(gym);
    }

    public Optional<Gym> find() {
        return gymRepository.findById(1L);
    }
}
