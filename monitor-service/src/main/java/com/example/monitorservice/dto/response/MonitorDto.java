package com.example.monitorservice.dto.response;

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