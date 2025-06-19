package com.jura_stefanovic.zavrsni.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceAppointmentDto {
    private Long serviceId;
    private LocalDateTime dateAndTime;
}
