package com.example.devalert.auth.controller;

import com.example.devalert.auth.dto.response.UserDto;
import com.example.devalert.auth.service.AdminService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping
    public ResponseEntity<@NonNull List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @PutMapping("/users/{id}/deactivate")
    public ResponseEntity<@NonNull String> deactivateUser(@PathVariable Long id) {
        adminService.deactivateUser(id);
        return ResponseEntity.ok("User (ID: " + id + ") has been deactivated.");
    }

}
