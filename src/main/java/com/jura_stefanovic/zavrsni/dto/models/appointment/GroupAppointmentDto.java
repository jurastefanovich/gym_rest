package com.jura_stefanovic.zavrsni.dto.models.appointment;

import com.jura_stefanovic.zavrsni.dto.models.extensions.AppointmentBasic;
import com.jura_stefanovic.zavrsni.model.entity.Appointment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupAppointmentDto extends AppointmentBasic {
    private String serviceTitle;
    private Long duration;
    private Integer currentParticipants = 0;
    private Integer maxParticipants;

    public GroupAppointmentDto(Appointment appointment) {
        super(appointment);
        if (appointment.getService() != null) {
            this.serviceTitle = appointment.getService().getTitle();
            this.duration = appointment.getService().getDurationSeconds();
            this.maxParticipants = appointment.getService().getMaxUsersPerGroupSession();
        }
        if (appointment.getUsers() != null) {
            this.currentParticipants = appointment.getUsers().size();
        }
    }

}