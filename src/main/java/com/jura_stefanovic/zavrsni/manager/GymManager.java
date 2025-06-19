package com.jura_stefanovic.zavrsni.manager;

import com.jura_stefanovic.zavrsni.repository.GymRepository;
import org.springframework.stereotype.Component;

@Component
public class GymManager {
    private final GymRepository gymRepository;

    public GymManager(GymRepository gymRepository) {
        this.gymRepository = gymRepository;
    }
}
