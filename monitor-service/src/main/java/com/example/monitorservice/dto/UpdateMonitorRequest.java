package com.example.monitorservice.dto;

public record UpdateMonitorRequest(
        String name,
        String url,
        Integer intervalMinutes,
        Integer threshold,
        boolean isActive
) {}