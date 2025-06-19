package com.jura_stefanovic.zavrsni.repository;


import com.jura_stefanovic.zavrsni.constants.Role;
import com.jura_stefanovic.zavrsni.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("Select u from User u where u.email =:email")
    Optional<User>  findByEmail(String email);

    @Query("Select u from User u where u.role = ?1")
    List<User> findAllByRole(Role role);

    @Query("SELECT u FROM User u WHERE u.role = :role ORDER BY FUNCTION('RANDOM') limit 1")
    User getRandomTrainer(Role role);

}
