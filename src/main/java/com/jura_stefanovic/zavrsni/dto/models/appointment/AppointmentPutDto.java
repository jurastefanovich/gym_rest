package com.jura_stefanovic.zavrsni.dto.models.appointment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppointmentPutDto {
    private LocalDateTime dateTime;
    private Long serviceId;
    private Long trainerId;
    private String notes;
}
