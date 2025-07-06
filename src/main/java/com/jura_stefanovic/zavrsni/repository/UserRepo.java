package com.jura_stefanovic.zavrsni.repository;


import com.jura_stefanovic.zavrsni.constants.Role;
import com.jura_stefanovic.zavrsni.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
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
    @Query("""
    SELECT COUNT(u)
    FROM User u
    WHERE u.role = 'USER'
      AND u.createdAt BETWEEN :startOfMonth AND :endOfMonth
""")
    Integer findNewUsersForThisMonth(
            @Param("startOfMonth") LocalDateTime startOfMonth,
            @Param("endOfMonth") LocalDateTime endOfMonth
    );

    @Query("""
    SELECT FUNCTION('to_char', u.createdAt, 'YYYY-MM') AS month,
           COUNT(u) AS count
    FROM User u
    WHERE u.role = 'USER'
    GROUP BY FUNCTION('to_char', u.createdAt, 'YYYY-MM')
    ORDER BY FUNCTION('to_char', u.createdAt, 'YYYY-MM')
""")
    List<Object[]> findUserRegistrationsPerMonth();

}
