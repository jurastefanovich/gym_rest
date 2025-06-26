package com.jura_stefanovic.zavrsni.dto.response;

import com.jura_stefanovic.zavrsni.model.entity.Gym;
import com.jura_stefanovic.zavrsni.model.entity.Schedule;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
public class GymResponseDto {
    public String name;
    public String location;
    public String email;
    public String phone;
    public List<Schedule> workingHours = new ArrayList<>();

    public GymResponseDto(Gym gym) {
        this.name = gym.getName();
        this.location = gym.getAddress();
        this.email = gym.getEmail();
        this.phone = gym.getPhone();
        this.workingHours = gym.getWorkingHours();
    }
}
