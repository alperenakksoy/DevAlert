package com.example.monitorservice.dto;

public record CreateMonitorRequest(
        String name,
        String url,
        Integer intervalMinutes,
        Integer threshold
) {}