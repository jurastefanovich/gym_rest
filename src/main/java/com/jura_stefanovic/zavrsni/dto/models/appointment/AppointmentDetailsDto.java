package com.jura_stefanovic.zavrsni.dto.models.appointment;

import com.jura_stefanovic.zavrsni.dto.models.extensions.AppointmentBasic;
import com.jura_stefanovic.zavrsni.model.entity.Appointment;
import com.jura_stefanovic.zavrsni.model.entity.Statistics;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class AppointmentDetailsDto extends AppointmentBasic {
    private Integer numberOfUsers = 0;
    private Integer maxNumberOfUsers = 0;
    @Nullable
    private Statistics statistics;

    private String status;
    private String serviceTitle;
    private String trainerName;
    private Long trainerId;
    private boolean isIndividual;
    private String duration;
    private boolean isIncluded;
    @Nullable
    private String notes;
    @Nullable
    private Long serviceId;

    public AppointmentDetailsDto() {
    }

    public AppointmentDetailsDto(Appointment appointment, boolean isIncluded) {
        super(appointment);
        if (appointment.isDone()) {
            this.statistics = appointment.getStatistics();
        }
        this.numberOfUsers = appointment.getNumberOfUsers();
        this.status = String.valueOf(appointment.getStatus());
        this.serviceTitle = appointment.getService().getTitle();
        if (appointment.getTrainer() != null) {
            this.trainerName = appointment.getTrainer().getFullName();
            this.trainerId = appointment.getTrainer().getId();
        }
        if (appointment.getService().getDurationSeconds() != null) {
            this.duration = String.valueOf(appointment.getService().getDurationSeconds());
            this.maxNumberOfUsers = appointment.getService().getMaxUsersPerGroupSession();
            this.serviceId = appointment.getService().getId();
        }

        this.isIncluded = isIncluded;
        if (appointment.getDescription() != null) {
            this.notes = appointment.getDescription();
        }
    }
}
