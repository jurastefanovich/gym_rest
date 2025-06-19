package com.jura_stefanovic.zavrsni.utils;

import com.jura_stefanovic.zavrsni.model.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
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
        LocalDateTime base = LocalDateTime.now().withMinute(0).withSecond(0).plusDays(1);
        for (int i = 0; i < 1000; i++) {
            LocalDateTime candidate = base.plusHours(i);
            boolean conflict = used.stream().anyMatch(dt -> dt.equals(candidate));
            if (!conflict) return candidate;
        }
        return null;
    }
}
