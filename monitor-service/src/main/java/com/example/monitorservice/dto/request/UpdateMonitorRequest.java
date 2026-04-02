package com.example.monitorservice.dto.request;

public record UpdateMonitorRequest(
        String name,
        String url,
        Integer intervalMinutes,
        Integer threshold,
        Boolean isActive
) {}