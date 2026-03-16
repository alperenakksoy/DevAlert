package com.example.devalert.auth.repository;

import com.example.devalert.auth.entity.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<@NonNull User,@NonNull Long> {
    Optional<User> findByEmail(String email);
}