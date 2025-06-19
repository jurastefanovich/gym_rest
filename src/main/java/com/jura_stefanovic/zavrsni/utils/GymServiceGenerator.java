package com.jura_stefanovic.zavrsni.utils;

import com.jura_stefanovic.zavrsni.manager.GymServiceManger;
import com.jura_stefanovic.zavrsni.model.entity.GymService;
import com.jura_stefanovic.zavrsni.model.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@AllArgsConstructor
public class GymServiceGenerator {
    private final GymServiceManger gymServiceManger;

    public void createGymService(String title, String description, Long duration, User trainer, boolean individual) {
        GymService gymService = new GymService();
        gymService.setTitle(title);
        gymService.setIndividual(individual);
        gymService.setDescription(description);
        gymService.setDurationSeconds(duration);
        gymService.setMaxUsersPerGroupSession(new Random().nextInt(20));
        if (trainer != null) {
            gymService.setNeedsTrainer(true);
            gymService.setTrainer(trainer);
        }

        gymServiceManger.save(gymService);
    }

    public void createDefaultGymServices() {
        System.out.println("""
    			=========================================================================================
				=================================GENERATING CUSTOM SERVICES=================================
				=========================================================================================
				""");

    }
}
