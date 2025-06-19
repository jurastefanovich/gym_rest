package com.jura_stefanovic.zavrsni.dto.models.appointment;

import com.jura_stefanovic.zavrsni.dto.models.extensions.AppointmentBasic;
import com.jura_stefanovic.zavrsni.model.entity.Appointment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.internal.build.AllowNonPortable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentListDto extends AppointmentBasic {
    private String serviceTitle;
    private String trainerName;
    public AppointmentListDto(Appointment appointment) {
        super(appointment);
        this.serviceTitle = appointment.getService().getTitle();
        this.trainerName = appointment.getTrainer().getFullName();
    }
}
