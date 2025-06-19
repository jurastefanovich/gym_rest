package com.jura_stefanovic.zavrsni.repository;

import com.jura_stefanovic.zavrsni.model.entity.Gym;
import com.jura_stefanovic.zavrsni.model.entity.GymService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GymRepository  extends JpaRepository<Gym, Long> {
}
