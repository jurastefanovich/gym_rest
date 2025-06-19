package com.jura_stefanovic.zavrsni.utils;

import com.jura_stefanovic.zavrsni.constants.Exercise;
import com.jura_stefanovic.zavrsni.manager.GymServiceManger;
import com.jura_stefanovic.zavrsni.model.entity.GymService;
import com.jura_stefanovic.zavrsni.model.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        gymService.setMaxUsersPerGroupSession(new Random().nextInt(20) + 1); // Avoid 0 users
        if (individual) {
            gymService.setMaxUsersPerGroupSession(1);
        }
        if (trainer != null) {
            gymService.setNeedsTrainer(true);
            gymService.setTrainer(trainer);
        }

        // Randomly assign exercises
        List<Exercise> allExercises = List.of(Exercise.values());
        Random random = new Random();

        int numberOfExercises = random.nextInt(5) + 3; // 3 to 7 exercises
        List<Exercise> randomExercises = IntStream.range(0, numberOfExercises)
                .mapToObj(i -> allExercises.get(random.nextInt(allExercises.size())))
                .collect(Collectors.toList());

        gymService.setExercises(randomExercises);

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
