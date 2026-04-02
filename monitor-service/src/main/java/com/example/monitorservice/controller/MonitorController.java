package com.example.monitorservice.controller;

import com.example.monitorservice.dto.request.CreateMonitorRequest;
import com.example.monitorservice.dto.request.UpdateMonitorRequest;
import com.example.monitorservice.dto.response.MonitorDto;
import com.example.monitorservice.service.JwtService;
import com.example.monitorservice.service.MonitorService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/monitors")
@RequiredArgsConstructor
public class MonitorController {

    private final MonitorService monitorService;
    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<@NonNull MonitorDto> createMonitor(
            @RequestBody CreateMonitorRequest request,
            @RequestHeader("Authorization") String authHeader
    ) {
        Long userId = extractUserId(authHeader);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(monitorService.createMonitor(userId, request));
    }

    @GetMapping
    public ResponseEntity<@NonNull List<MonitorDto>> getUserMonitors(
            @RequestHeader("Authorization") String authHeader
    ) {
        Long userId = extractUserId(authHeader);
        return ResponseEntity.ok(monitorService.getUserMonitors(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<@NonNull MonitorDto> getMonitor(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader
    ) {
        Long userId = extractUserId(authHeader);
        return ResponseEntity.ok(monitorService.getMonitor(userId, id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<@NonNull MonitorDto> updateMonitor(
            @PathVariable Long id,
            @RequestBody UpdateMonitorRequest request,
            @RequestHeader("Authorization") String authHeader
    ) {
        Long userId = extractUserId(authHeader);
        return ResponseEntity.ok(monitorService.updateMonitor(userId, id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<@NonNull Void> deleteMonitor(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader
    ) {
        Long userId = extractUserId(authHeader);
        monitorService.deleteMonitor(userId, id);
        return ResponseEntity.noContent().build();
    }

    private Long extractUserId(String authHeader) {
        String token = authHeader.substring(7);
        return jwtService.extractUserId(token);
    }
}