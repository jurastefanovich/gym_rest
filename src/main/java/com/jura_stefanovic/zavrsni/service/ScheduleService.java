package com.jura_stefanovic.zavrsni.service;

import com.jura_stefanovic.zavrsni.constants.Day;
import com.jura_stefanovic.zavrsni.manager.AppointmentManager;
import com.jura_stefanovic.zavrsni.model.entity.Appointment;
import com.jura_stefanovic.zavrsni.model.entity.GymService;
import com.jura_stefanovic.zavrsni.model.entity.Schedule;
import com.jura_stefanovic.zavrsni.repository.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final AppointmentManager appointmentManager;
//    public List<LocalTime> getAvailableTimes(String dateStr, Long trainerId) {
//        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
//
//        // 1. Get day of week
//        DayOfWeek dayOfWeek = date.getDayOfWeek();
//
//        // 2. Fetch trainer's schedule for that day
//        List<Schedule> schedules = scheduleRepository.findByTrainerIdAndDay(trainerId, Day.valueOf(dayOfWeek.name()));
//        if (schedules.isEmpty()) return Collections.emptyList();
//
//        // 3. Fetch all appointments on that date
//        List<Appointment> appointments = appointmentManager.findByTrainerIdAndDateBetween(
//                trainerId,
//                date.atStartOfDay(),
//                date.atTime(LocalTime.MAX)
//        );
//
//        // 4. Generate all possible time slots based on schedule + service duration
//        List<LocalTime> availableSlots = new ArrayList<>();
//        for (Schedule schedule : schedules) {
//            GymService service = schedule.get // or fetch explicitly
//            int duration = service.getSessionDurationInMinutes();
//
//            LocalTime slot = schedule.getStart().toLocalTime();
//            while (slot.plusMinutes(duration).isBefore(schedule.getEnd().toLocalTime()) ||
//                    slot.plusMinutes(duration).equals(schedule.getEnd().toLocalTime())) {
//
//                boolean isAvailable = isSlotAvailable(slot, duration, appointments, service.getMaxUsersPerGroupSession());
//                if (isAvailable) {
//                    availableSlots.add(slot);
//                }
//                slot = slot.plusMinutes(duration);
//            }
//        }
//
//        return availableSlots;
//    }
    private boolean isSlotAvailable(LocalTime slotStart, int duration,
                                    List<Appointment> appointments,
                                    int maxUsersPerGroup) {

        LocalTime slotEnd = slotStart.plusMinutes(duration);

        for (Appointment appt : appointments) {
            LocalTime apptStart = appt.getDate().toLocalTime();
            LocalTime apptEnd = apptStart.plusMinutes(duration);

            boolean overlaps = !(slotEnd.isBefore(apptStart) || slotStart.isAfter(apptEnd));
            if (overlaps) {
                if (appt.isIndividual()) {
                    return false;
                } else if (appt.getUsers().size() >= maxUsersPerGroup) {
                    return false;
                }
            }
        }

        return true;
    }

}
