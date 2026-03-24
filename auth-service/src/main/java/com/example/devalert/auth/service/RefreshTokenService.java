package com.example.devalert.auth.service;

import com.example.devalert.auth.entity.RefreshToken;
import com.example.devalert.auth.repository.RefreshTokenRepository;
import com.example.devalert.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    private static final long REFRESH_TOKEN_EXPIRATION = 7L * 24 * 60 * 60 * 1000;

    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found")));

        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiry(Instant.now().plusMillis(REFRESH_TOKEN_EXPIRATION));
        refreshToken.setRevoked(false);

        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public boolean verifyExpiration(RefreshToken token) {
        if (token.getExpiry().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token was expired. Please make a new signing request");
        }
        if (token.isRevoked()) {
            throw new RuntimeException("Refresh token has been revoked");
        }
        return true;
    }
}