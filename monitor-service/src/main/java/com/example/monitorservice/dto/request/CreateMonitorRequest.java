package com.example.monitorservice.dto.request;

public record CreateMonitorRequest(
        String name,
        String url,
        Integer intervalMinutes,
        Integer threshold
) {}