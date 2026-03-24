package com.example.devalert.auth.dto.response;

import java.time.LocalDateTime;

public record UserDto(
        Long id,
        String email,
        String role,
        boolean isActive,
        LocalDateTime createdAt
) {}