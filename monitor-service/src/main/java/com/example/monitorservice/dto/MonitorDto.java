package com.example.monitorservice.dto;

import java.time.LocalDateTime;

public record MonitorDto(
        Long id,
        String name,
        String url,
        Integer intervalMinutes,
        Integer threshold,
        boolean isActive,
        LocalDateTime lastCheckedAt
) {}