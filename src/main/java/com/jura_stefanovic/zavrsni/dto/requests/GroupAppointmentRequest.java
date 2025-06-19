package com.jura_stefanovic.zavrsni.dto.requests;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupAppointmentRequest{
    Long serviceId;
    LocalDateTime dateTime;
    Long trainerId;
    @Nullable
    String description;
}
