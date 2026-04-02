package com.example.monitorservice.service;

import com.example.monitorservice.dto.request.CreateMonitorRequest;
import com.example.monitorservice.dto.request.UpdateMonitorRequest;
import com.example.monitorservice.dto.response.MonitorDto;
import com.example.monitorservice.entity.Monitor;
import com.example.monitorservice.repository.MonitorRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MonitorService {
    private final MonitorRepository monitorRepository;

    @Transactional
    public MonitorDto createMonitor(Long userId,CreateMonitorRequest request) {

        Monitor monitor = new Monitor();
        monitor.setUserId(userId);
        monitor.setName(request.name());
        monitor.setUrl(request.url());
        monitor.setIntervalMinutes(request.intervalMinutes());
        monitor.setThreshold(request.threshold());
        monitor.setIsActive(true);
        monitor.setLastCheckedAt(LocalDateTime.now());

        Monitor savedMonitor = monitorRepository.save(monitor);

        return toDto(savedMonitor);
}

    @Transactional(readOnly = true)
    public List<MonitorDto> getUserMonitors(Long userId) {

        List<Monitor> monitors = monitorRepository.findByUserId(userId);

        return monitors
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public MonitorDto getMonitor(Long userId,Long monitorId) {

       Monitor monitor = findMonitorOrThrow(userId,monitorId);

       return toDto(monitor);
    }

    @Transactional
    public MonitorDto updateMonitor(Long userId, Long monitorId, UpdateMonitorRequest request) {

        Monitor monitor = findMonitorOrThrow(userId,monitorId);

        if(request.name()!=null){
            monitor.setName(request.name());
        }
        if(request.url()!=null){
            monitor.setUrl(request.url());
        }
        if(request.intervalMinutes()!=null){
            monitor.setIntervalMinutes(request.intervalMinutes());
        }
        if(request.threshold()!=null){
            monitor.setThreshold(request.threshold());
        }
        if (request.isActive() != null) {
            monitor.setIsActive(request.isActive());
        }

        Monitor savedMonitor = monitorRepository.save(monitor);
        return toDto(savedMonitor);

    }
    @Transactional
    public void deleteMonitor(Long userId,Long monitorId) {

        Monitor monitor = findMonitorOrThrow(userId,monitorId);
        monitorRepository.delete(monitor);

    }

    private Monitor findMonitorOrThrow(Long userId, Long monitorId) {

        return monitorRepository.findByIdAndUserId(monitorId,userId)
                .orElseThrow(() -> new NoSuchElementException("Monitor not found"));
    }

    private MonitorDto toDto(Monitor monitor) {
        return new MonitorDto(
                monitor.getId(),
                monitor.getName(),
                monitor.getUrl(),
                monitor.getIntervalMinutes(),
                monitor.getThreshold(),
                monitor.getIsActive(),
                monitor.getLastCheckedAt()
        );
}

}
