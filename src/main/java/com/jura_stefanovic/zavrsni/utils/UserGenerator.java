package com.jura_stefanovic.zavrsni.utils;

import com.jura_stefanovic.zavrsni.constants.Role;
import com.jura_stefanovic.zavrsni.manager.UserManager;
import com.jura_stefanovic.zavrsni.model.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class UserGenerator {
    private final UserManager userManager;
    private final PasswordEncoder passwordEncoder;

    public UserGenerator(UserManager userManager, PasswordEncoder passwordEncoder) {
        this.userManager = userManager;
        this.passwordEncoder = passwordEncoder;
    }

    private static final List<String> MASTER_SPECIALIZATIONS = List.of(
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

    private List<String> getRandomSpecializations() {
        Random random = new Random();
        int numberOfSpecializations = random.nextInt(4) + 2; // between 2 and 5

        List<String> shuffled = new ArrayList<>(MASTER_SPECIALIZATIONS);
        Collections.shuffle(shuffled);

        return shuffled.subList(0, numberOfSpecializations);
    }

    public User getRandomTrainer(){
        return userManager.getRandomTrainer();
    }

    public void createAdminUser() {
        System.out.println("""
    			=========================================================================================
				=================================GENERATING ADMIN ROLE=================================
				=========================================================================================
				""");
        if (userManager.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin")); // secure it!
            admin.setFirstName("System");
            admin.setLastName("Administrator");
            admin.setEmail("admin@example.com");
            admin.setPhoneNumber("1234567890");
            admin.setRole(Role.ADMIN);

            userManager.save(admin);
            System.out.println("Admin user created.");
        } else {
            System.out.println("Admin already exists");
        }
    }


    public void createTrainerUser(String username, String password, String firstName, String lastName, String email, String phoneNumber, String desc) {
        System.out.println("""
    			=========================================================================================
				=================================GENERATING TRAINERS ROLE=================================
				=========================================================================================
				""");
        if (userManager.findByUsername(username).isEmpty()) {
            User trainer = new User();
            trainer.setUsername(username);
            trainer.setPassword(passwordEncoder.encode(password)); // secure it!
            trainer.setFirstName(firstName);
            trainer.setLastName(lastName);
            trainer.setEmail(email);
            trainer.setPhoneNumber(phoneNumber);
            trainer.setRole(Role.TRAINER);
            trainer.setDescription(desc);
            trainer.setSpecialisation(getRandomSpecializations());

            userManager.save(trainer);
            System.out.println("Trainer created");
        } else {
            System.out.println("Trainer creation failed, username not unique");
        }
    }

    public void creatingUser(String username, String password, String firstName, String lastName, String email, String phoneNumber) {
        System.out.println("""
    			=========================================================================================
				=================================GENERATING USER ROLE=================================
				=========================================================================================
				""");
        if (userManager.findByUsername(username).isEmpty()) {
            User user = new User();
            user.setUsername(username);
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime startOfYear = LocalDateTime.of(now.getYear(), Month.JANUARY, 1, 0, 0);

            long secondsBetween = ChronoUnit.SECONDS.between(startOfYear, now);
            long randomSeconds = ThreadLocalRandom.current().nextLong(secondsBetween + 1);

            LocalDateTime randomPastTime = startOfYear.plusSeconds(randomSeconds);

            user.setLastLoginTime(randomPastTime);
            user.setPassword(passwordEncoder.encode(password)); // secure it!
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPhoneNumber(phoneNumber);
            user.setRole(Role.USER);
            userManager.save(user);
            System.out.println("User created");
        } else {
            System.out.println("User creation failed, username not unique");
        }
    }


}
