package com.jura_stefanovic.zavrsni.dto.models.extensions;

import com.jura_stefanovic.zavrsni.model.entity.Appointment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AppointmentBasic {
    private Long id;
    private String date;
    private String status;

    public AppointmentBasic(Appointment appointment) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm");
        this.date = appointment.getDate().format(formatter);
        this.status = appointment.getStatus().toString();
        this.id = appointment.getId();
    }
}
