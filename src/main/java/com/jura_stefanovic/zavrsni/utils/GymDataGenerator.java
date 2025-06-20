package com.jura_stefanovic.zavrsni.utils;

import com.jura_stefanovic.zavrsni.model.entity.Gym;
import com.jura_stefanovic.zavrsni.model.entity.Schedule;
import com.jura_stefanovic.zavrsni.constants.Day;
import com.jura_stefanovic.zavrsni.repository.GymRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
@Component
@AllArgsConstructor
public class GymDataGenerator {
    private final GymRepository gymRepository;

    public void createIronPathGym() {
        Gym gym = new Gym();
        gym.setName("Iron Path");
        gym.setDescription("Premium fitness center offering 24/7 gym access.");
        gym.setPhone("+123456789");
        gym.setEmail("contact@ironpath.com");
        gym.setAddress("123 Iron Path St, Fitness City");
        gym.setLogoUrl("https://example.com/logo.png");

        // Working hours: 8 AM to 11 PM UTC for every day
        Arrays.stream(Day.values()).forEach(day -> {
            Schedule schedule = new Schedule();
            schedule.setDay(day);
            schedule.setStart(LocalDateTime.of(LocalDate.now(), LocalTime.of(8, 0)));  // 08:00 today
            schedule.setEnd(LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 0)));   // 23:00 today
            gym.getWorkingHours().add(schedule);
        });


        gymRepository.save(gym);
    }
}
