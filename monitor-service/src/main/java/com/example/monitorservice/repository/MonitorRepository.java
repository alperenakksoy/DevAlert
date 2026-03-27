package com.example.monitorservice.repository;

import com.example.monitorservice.entity.Monitor;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MonitorRepository extends JpaRepository<@NonNull Monitor,@NonNull Long> {

    List<Monitor> findByUserId(Long userId);

    @Query(value = "SELECT * FROM monitors WHERE is_active = true AND " +
            "(last_checked_at IS NULL OR last_checked_at + (interval_minutes * interval '1 minute') <= NOW())",
            nativeQuery = true)
    List<Monitor> findDueForCheck();

}
