package com.jura_stefanovic.zavrsni.model.entity;

import com.jura_stefanovic.zavrsni.dto.requests.GymServiceRequest;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.Duration;
import java.util.List;

@Entity
@Table(name = "gym_service")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class GymService implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 500)
    private String description;

    private Long durationSeconds;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id")
    private User trainer;

    private boolean individual = false;
    private boolean needsTrainer = false;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments;

    private int maxUsersPerGroupSession;
    private int sessionDurationInMinutes;

    private boolean active = true;

    public GymService(GymServiceRequest request) {
        this.title = request.getTitle();
        this.description = request.getDescription();
        this.individual = request.isIndividual();
        this.needsTrainer = request.isTrainerRequired();
        if (request.isIndividual()) {
            this.maxUsersPerGroupSession = 1;
            this.durationSeconds = null;
        } else {
            this.maxUsersPerGroupSession = request.getMaxUsersPerGroupSession();
            if (request.getDurationSeconds() != null) {
                this.durationSeconds = request.getDurationSeconds();
            }
        }
    }

    @Override
    public String toString() {
        return "GymService{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", durationSeconds=" + durationSeconds +
                ", individual=" + individual +
                ", needsTrainer=" + needsTrainer +
                ", maxUsersPerGroupSession=" + maxUsersPerGroupSession +
                ", sessionDurationInMinutes=" + sessionDurationInMinutes +
                ", active=" + active +
                '}';
    }
}
