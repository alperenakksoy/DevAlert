package com.example.devalert.auth.service;

import com.example.devalert.auth.dto.response.AuthResponse;
import com.example.devalert.auth.dto.request.LoginRequest;
import com.example.devalert.auth.dto.request.RegisterRequest;
import com.example.devalert.auth.entity.RefreshToken;
import com.example.devalert.auth.entity.User;
import com.example.devalert.auth.enums.Role;
import com.example.devalert.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    private final StringRedisTemplate redisTemplate;

    private static final int MAX_FAILED_ATTEMPTS = 5;
    private static final int LOCK_TIME_DURATION = 15; //

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("Email is already registered");
        }

        User user = new User();
        user.setEmail(request.email());
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user.setRole(Role.USER);
        user.setActive(true);
        user = userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

        return new AuthResponse(jwtToken, refreshToken.getToken());
    }

    public AuthResponse login(LoginRequest request) {
        String redisKey = "login_attempts:" + request.email();

        String attemptsStr = redisTemplate.opsForValue().get(redisKey);
        int attempts = attemptsStr != null ? Integer.parseInt(attemptsStr) : 0;

        if (attempts >= MAX_FAILED_ATTEMPTS) {
            throw new RuntimeException("Rate limit exceeded. Try again in 15 minutes.");
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()
                    )
            );

            redisTemplate.delete(redisKey);

        } catch (AuthenticationException e) {
            Long currentAttempts = redisTemplate.opsForValue().increment(redisKey);

            if (currentAttempts != null && currentAttempts == 1) {
                redisTemplate.expire(redisKey, Duration.ofMinutes(LOCK_TIME_DURATION));
            }

            throw new IllegalArgumentException("Invalid email or password");
        }

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String jwtToken = jwtService.generateToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

        return new AuthResponse(jwtToken, refreshToken.getToken());
    }

    public AuthResponse refreshToken(String requestRefreshToken) {
        return refreshTokenService.findByToken(requestRefreshToken)
                .filter(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtService.generateToken(user);
                    return new AuthResponse(accessToken, requestRefreshToken);
                })
                .orElseThrow(() -> new RuntimeException("Refresh token is not in database or is expired/revoked!"));
    }
}