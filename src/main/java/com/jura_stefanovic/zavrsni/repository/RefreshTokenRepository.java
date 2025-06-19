package com.jura_stefanovic.zavrsni.repository;

import com.jura_stefanovic.zavrsni.model.auth.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findRefreshTokenByToken(String token);
    @Query("SELECT r FROM RefreshToken r WHERE r.login = ?1 AND r.expiration < ?2 ")
    List<RefreshToken> findExpiredRefreshTokensByEmail(String email, LocalDateTime now);

    @Query("Select r from RefreshToken r where r.user.email = ?1")
    Optional<RefreshToken> findByUserEmail(String email);
}
