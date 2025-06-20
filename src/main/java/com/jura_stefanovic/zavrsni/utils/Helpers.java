package com.jura_stefanovic.zavrsni.utils;

import com.jura_stefanovic.zavrsni.model.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Component
public class Helpers {
    public  <T> T getRandom(List<T> list) {
        return list.get(new Random().nextInt(list.size()));
    }

    public List<User> getRandomClients(List<User> clients, int count) {
        List<User> mutableClients = new ArrayList<>(clients); // make it mutable
        Collections.shuffle(mutableClients);
        return mutableClients.subList(0, Math.min(count, mutableClients.size()));
    }

    public  LocalDateTime getNextAvailableSlot(List<LocalDateTime> used) {
        LocalDateTime now = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
        LocalDateTime start = now.minusMonths(3).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime end = now.plusMonths(3).withMinute(0).withSecond(0).withNano(0);

        // Calculate total hours between start and end
        long totalHours = ChronoUnit.HOURS.between(start, end);

        // Generate all candidate slots at hour precision
        List<LocalDateTime> candidates = new ArrayList<>();
        for (long i = 0; i <= totalHours; i++) {
            candidates.add(start.plusHours(i));
        }

        // Shuffle to randomize order
        Collections.shuffle(candidates);

        // Find first candidate that is NOT in 'used'
        for (LocalDateTime candidate : candidates) {
            boolean conflict = used.stream().anyMatch(dt -> dt.equals(candidate));
            if (!conflict) {
                return candidate;
            }
        }

        // No available slot found
        return null;
    }
}
