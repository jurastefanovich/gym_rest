package com.jura_stefanovic.zavrsni.dto.response;

import com.jura_stefanovic.zavrsni.constants.Exercise;
import com.jura_stefanovic.zavrsni.dto.models.extensions.AppointmentBasic;
import com.jura_stefanovic.zavrsni.dto.models.users.UserDto;
import com.jura_stefanovic.zavrsni.model.entity.Appointment;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FinishAppointmentResponseDto extends AppointmentBasic {
    List<UserDto> users;
    List<String> exercises;
    UserDto trainer;
    String serviceTitle;
    @Nullable
    String notes;

    public FinishAppointmentResponseDto(Appointment appointment) {
        super(appointment);
        this.users = appointment.getUsers().stream().map(UserDto::new).toList();
        this.exercises = appointment.getService().getExercises().stream()
                .map(val -> (val.name()))
                .collect(Collectors.toList());
        this.trainer = new UserDto(appointment.getTrainer());
        this.serviceTitle = appointment.getService().getTitle();
        this.notes = appointment.getDescription();
    }
    
}
