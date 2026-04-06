package com.finance.dashbaord.controller;

import com.finance.dashbaord.entity.AuditLog;
import com.finance.dashbaord.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/audit")
@RequiredArgsConstructor
public class LogController {
    private final AuditLogRepository auditLogRepository;
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<AuditLog> getAllLogs() {

        return auditLogRepository.findAll();
    }
}
