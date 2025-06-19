package com.jura_stefanovic.zavrsni.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jura_stefanovic.zavrsni.constants.Status;
import com.jura_stefanovic.zavrsni.dto.models.appointment.AppointmentPutDto;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@Table(name = "appointment")
@AllArgsConstructor
@NoArgsConstructor
public class Appointment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "appointment", cascade = CascadeType.ALL)
    @Nullable
    private Statistics statistics;

    @ManyToMany
    @JoinTable(
            name = "user_appointments",
            joinColumns = @JoinColumn(name = "appointment_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;

    @Enumerated(EnumType.STRING)
    private Status status;

    @JsonFormat(pattern = "dd/MM/yyyy, HH:mm")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private User trainer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gym_service_id")
    private GymService service;

    @Nullable
    @Column(length = 1000)
    private String description;

    private boolean individual;


    public Integer getNumberOfUsers() {
        return this.users.size();
    }

    public boolean isDone() {
        return this.status.equals(Status.FINISHED);
    }

    public void addUser(User loggedInUser) {
        this.users.add(loggedInUser);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Appointment that = (Appointment) object;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
