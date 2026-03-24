package com.example.devalert.auth.entity;

import com.example.devalert.auth.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(unique = true, nullable = false)
    private String email;

    @Setter
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Setter
    @Column(name = "is_active",nullable = false)
    private boolean isActive = true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
