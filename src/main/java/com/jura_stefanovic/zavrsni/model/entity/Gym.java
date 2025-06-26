package com.jura_stefanovic.zavrsni.model.entity;

import com.jura_stefanovic.zavrsni.dto.response.GymResponseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "gym")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Gym implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String phone;

    private String email;

    private String address;

    private String logoUrl;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "gym_id")
    private List<Schedule> workingHours = new ArrayList<>();

    public void updateGym(GymResponseDto dto) {
        this.name = dto.getName();
        this.address = dto.getLocation();
        this.phone = dto.getPhone();
        this.email = dto.getEmail();
    }
}
