package com.jura_stefanovic.zavrsni.repository;

import com.jura_stefanovic.zavrsni.model.entity.GymService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GymServiceRepository extends JpaRepository<GymService, Long> {
    @Query("select gs from GymService gs where lower(gs.title) =?1 and gs.active = true")
    GymService findByTitle(String title);

    @Query("select gs from GymService gs where gs.active = true order by gs.title ASC")
    List<GymService> findAll();
    @Query("select gs from GymService gs where gs.id = ?1 and gs.active = true")
    Optional<GymService> findById(Long id);

    @Query("select gs from GymService  gs where gs.active = true and gs.individual = false and gs.needsTrainer = true order by gs.title ASC")
    List<GymService> findAllGroup();
}
