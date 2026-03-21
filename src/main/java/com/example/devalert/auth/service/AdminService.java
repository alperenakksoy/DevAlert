package com.example.devalert.auth.service;

import com.example.devalert.auth.dto.response.UserDto;
import com.example.devalert.auth.entity.User;
import com.example.devalert.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserDto(
                        user.getId(),
                        user.getEmail(),
                        user.getRole().name(),
                        user.isActive(),
                        user.getCreatedAt()
                ))
                .toList();
    }

    @Transactional
    public void deactivateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("User not found!"));

        user.setActive(false);
        userRepository.save(user);
    }
}