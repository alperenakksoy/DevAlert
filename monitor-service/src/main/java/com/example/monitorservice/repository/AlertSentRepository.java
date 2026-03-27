package com.example.monitorservice.repository;

import com.example.monitorservice.entity.AlertsSent;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlertSentRepository extends JpaRepository<@NonNull AlertsSent,@NonNull Long> {
    Optional<AlertsSent> findTopByMonitorIdOrderBySentAtDesc(Long monitorId);
}
