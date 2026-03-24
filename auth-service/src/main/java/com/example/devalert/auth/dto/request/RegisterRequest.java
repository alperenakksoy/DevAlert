package com.example.devalert.auth.dto.request;

public record RegisterRequest(
        String email,
        String password
) {}