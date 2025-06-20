package com.jura_stefanovic.zavrsni.utils;

import com.jura_stefanovic.zavrsni.constants.Status;
import com.jura_stefanovic.zavrsni.manager.AppointmentManager;
import com.jura_stefanovic.zavrsni.manager.GymServiceManger;
import com.jura_stefanovic.zavrsni.manager.UserManager;
import com.jura_stefanovic.zavrsni.model.entity.Appointment;
import com.jura_stefanovic.zavrsni.model.entity.GymService;
import com.jura_stefanovic.zavrsni.model.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class AppointmentGenerator {
    private final UserManager userManager;
    private final GymServiceManger gymServiceManger;
    private final AppointmentManager appointmentManager;
    private final Helpers helpers;
    public void generateDummyGroupAppointments(int count) {
        final boolean[] done = {false};

        // Spinner Thread
        Thread spinner = new Thread(() -> {
            String[] spinnerChars = {"|", "/", "-", "\\"};
            int i = 0;
            while (!done[0]) {
                System.out.print("\rCreating dummy appointments... " + spinnerChars[i++ % spinnerChars.length]);
                try {
                    Thread.sleep(100); // update spinner every 100ms
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            System.out.print("\r✅ Dummy appointments created successfully.           \n");
        });

        spinner.start();

        // Actual Appointment Creation
        try {
            List<User> allUsers = userManager.findAll();
            List<User> trainers = allUsers.stream().filter(User::isTrainer).toList();
            List<User> clients = allUsers.stream().filter(user -> !user.isTrainer()).toList();
            List<GymService> groupServices = gymServiceManger.findAll()
                    .stream()
                    .filter(service -> !service.isIndividual() && service.getMaxUsersPerGroupSession() > 0)
                    .toList();

            if (trainers.isEmpty() || clients.size() < 2 || groupServices.isEmpty()) {
                throw new IllegalStateException("Not enough valid data to generate appointments.");
            }

            List<LocalDateTime> usedSlots = appointmentManager.findAll()
                    .stream()
                    .map(Appointment::getDate)
                    .collect(Collectors.toList());

            int attempts = 0;
            int created = 0;

            while (created < count && attempts < count * 10) {
                attempts++;

                User trainer = helpers.getRandom(trainers);
                GymService service = helpers.getRandom(groupServices);
                int maxUsers = service.getMaxUsersPerGroupSession();
                int numberOfUsers;

                if (maxUsers <= 2) {
                    numberOfUsers = maxUsers;  // or just 2 if you want a minimum of 2
                } else {
                    numberOfUsers = 2 + new Random().nextInt(maxUsers - 1); // maxUsers - 1 is positive here
                }

                List<User> participants = helpers.getRandomClients(clients, numberOfUsers);
                LocalDateTime slot = helpers.getNextAvailableSlot(usedSlots);
                if (slot == null || usedSlots.contains(slot)) continue;

                Appointment appointment = new Appointment();
                appointment.setTrainer(trainer);
                appointment.setUsers(participants);
                appointment.setService(service);
                appointment.setStatus(Status.ACTIVE);
                appointment.setDate(slot);
                appointment.setIndividual(false);

                String serviceName = service.getTitle();
                String trainerFullName = trainer.getFirstName() + " " + trainer.getLastName();
                String dateFormatted = slot.toLocalDate().format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
                String description = String.format(
                        "Join our energizing %s session with %s on %s. " +
                                "This group session is crafted to boost performance and motivation in a fun, guided environment.",
                        serviceName,
                        trainerFullName,
                        dateFormatted
                );

                appointment.setDescription(description);
                appointmentManager.save(appointment);
                usedSlots.add(slot);
                created++;
            }

        } finally {
            done[0] = true; // stop spinner
            try {
                spinner.join(); // wait for spinner thread to finish
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }





}
