package com.example.monitorservice.repository;

import com.example.monitorservice.entity.CheckLog;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckLogRepository extends JpaRepository<@NonNull CheckLog,@NonNull Long> {
    List<CheckLog> findByMonitorIdOrderByCheckedAtDesc(Long monitorId);
}
