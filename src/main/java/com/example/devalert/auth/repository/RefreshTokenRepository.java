package com.example.devalert.auth.repository;

import com.example.devalert.auth.entity.RefreshToken;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<@NonNull RefreshToken,@NonNull Long> {
    Optional<RefreshToken> findByToken(String token);
}
