package com.jura_stefanovic.zavrsni.dto.models.gym;

import com.jura_stefanovic.zavrsni.model.entity.Gym;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GymDto {
    private String name;

    private String description;

    private String phone;

    private String email;

    private String address;

    private String logoUrl;

    public GymDto(Gym gym) {
        this.name = gym.getName();
        this.description = gym.getDescription();
        this.phone = gym.getPhone();
        this.email = gym.getEmail();
        this.address = gym.getAddress();
        this.logoUrl = gym.getLogoUrl();
    }
}
