package com.example.monitorservice.controller;

import com.example.monitorservice.dto.request.CreateMonitorRequest;
import com.example.monitorservice.dto.request.UpdateMonitorRequest;
import com.example.monitorservice.dto.response.MonitorDto;
import com.example.monitorservice.service.MonitorService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/monitors")
@RequiredArgsConstructor
public class MonitorController {

    private final MonitorService monitorService;

    @PostMapping
    public ResponseEntity<@NonNull MonitorDto> createMonitor(
            @RequestBody CreateMonitorRequest request,
            Authentication auth
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(monitorService.createMonitor(getUserId(auth), request));
    }

    @GetMapping
    public ResponseEntity<@NonNull List<MonitorDto>> getUserMonitors(Authentication auth) {
        return ResponseEntity.ok(monitorService.getUserMonitors(getUserId(auth)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<@NonNull MonitorDto> getMonitor(
            @PathVariable Long id,
            Authentication auth
    ) {
        return ResponseEntity.ok(monitorService.getMonitor(getUserId(auth), id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<@NonNull MonitorDto> updateMonitor(
            @PathVariable Long id,
            @RequestBody UpdateMonitorRequest request,
            Authentication auth
    ) {
        return ResponseEntity.ok(monitorService.updateMonitor(getUserId(auth), id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<@NonNull Void> deleteMonitor(
            @PathVariable Long id,
            Authentication auth
    ) {
        monitorService.deleteMonitor(getUserId(auth), id);
        return ResponseEntity.noContent().build();
    }

    private Long getUserId(Authentication auth) {
        return (Long) auth.getPrincipal();
    }
}