package com.example.monitorservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "monitors")
public class Monitor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id",nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String url;

    @Column(name = "interval_minutes",nullable = false)
    private Integer intervalMinutes;

    @Column(nullable = false)
    private Integer threshold;

    @Column(name = "is_active",nullable = false)
    private Boolean isActive = true;

    @Column(name = "last_checked_at",nullable = false)
    private LocalDateTime lastCheckedAt;

    @CreationTimestamp
    @Column(name = "created_at",updatable = false)
    private LocalDateTime createdAt;






}
