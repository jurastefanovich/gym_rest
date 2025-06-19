package com.jura_stefanovic.zavrsni.dto.models.appointment;

import com.jura_stefanovic.zavrsni.dto.models.extensions.AppointmentBasic;
import com.jura_stefanovic.zavrsni.model.entity.Appointment;
import lombok.Data;

@Data
public class AppointmentInServiceDto extends AppointmentBasic {
    private Integer numberOfUsers = 0;
    private Integer maxNumberOfUsers = 0;
    public AppointmentInServiceDto(Appointment appointment) {
        super(appointment);
        if (appointment.getService() != null) {
            this.numberOfUsers = appointment.getNumberOfUsers();
            this.maxNumberOfUsers = appointment.getService().getMaxUsersPerGroupSession();
        }

    }
}
