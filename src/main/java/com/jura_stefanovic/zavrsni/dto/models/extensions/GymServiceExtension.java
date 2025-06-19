package com.jura_stefanovic.zavrsni.dto.models.extensions;

import com.jura_stefanovic.zavrsni.model.entity.GymService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class GymServiceExtension implements Serializable {
    private String title;
    private Long id;
    private String description;
    private Long duration;

    public GymServiceExtension(GymService gymService) {
        this.title = gymService.getTitle();
        this.id = gymService.getId();
        this.description = gymService.getDescription();
        this.duration = gymService.getDurationSeconds();
    }
}
