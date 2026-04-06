package com.finance.dashbaord.record.service;

import com.finance.dashbaord.entity.AuditLog;
import com.finance.dashbaord.entity.FinancialRecord;
import com.finance.dashbaord.entity.RecordType;
import com.finance.dashbaord.entity.User;
import com.finance.dashbaord.record.dto.CreateRecordRequest;
import com.finance.dashbaord.record.dto.RecordResponse;
import com.finance.dashbaord.repository.AuditLogRepository;
import com.finance.dashbaord.repository.RecordRepository;
import com.finance.dashbaord.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;



import java.util.UUID;

@Service
@RequiredArgsConstructor

public class RecordService {
    private final RecordRepository recordRepository;
    private final AuditLogRepository auditLogRepository;
    private final UserRepository userRepository;

    @CacheEvict(value = {"summary", "category", "monthly"}, allEntries = true)
    public RecordResponse create(CreateRecordRequest  createRecordRequest)
    {
        User user = getCurrentUser();
        FinancialRecord record = FinancialRecord.builder()
                .amount(createRecordRequest.getAmount())
                .type(createRecordRequest.getType())
                .category(createRecordRequest.getCategory())
                .date(createRecordRequest.getDate())
                .note(createRecordRequest.getNote())
                .isDeleted(false)
                .user(user)
                .build();

        FinancialRecord saved = recordRepository.save(record);
        logAudit("CREATE",saved.getId(),"Record Created");
        return  mapToResponse(saved);
    }
    public Page<RecordResponse> getAll(Pageable pageable)
    {
        return recordRepository.findByIsDeletedFalse(pageable)
                .map(this::mapToResponse);
    }

    public RecordResponse update(UUID id, CreateRecordRequest  createRecordRequest)
    {
        FinancialRecord record = recordRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(()-> new RuntimeException("Record not found"));
        record.setAmount(createRecordRequest.getAmount());
        record.setCategory(createRecordRequest.getCategory());
        record.setDate(createRecordRequest.getDate());
        record.setNote(createRecordRequest.getNote());

        FinancialRecord updated = recordRepository.save(record);
        logAudit("UPDATE",updated.getId(),"Record Updated");
        return  mapToResponse(updated);
    }
    public void delete(UUID id) {
        FinancialRecord record = recordRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(()-> new RuntimeException("Record not found"));

        record.setDeleted(true);
        logAudit("DELETE",record.getId(),"Record Deleted");
    }

    private RecordResponse mapToResponse(FinancialRecord record) {
        return RecordResponse.builder()
                .id(record.getId())
                .amount(record.getAmount())
                .type(RecordType.valueOf(record.getType().name()))
                .category(record.getCategory())
                .date(record.getDate())
                .note(record.getNote())
                .build();
    }
    private void logAudit(String action, UUID entityId, String details) {
        auditLogRepository.save(AuditLog.builder()
                .action(action)
                .entityType("RECORD")
                .entityId(entityId)
                .timestamp(java.time.LocalDateTime.now())
                .details(details)
                .build());
    }

    ///Helper method  to get current user
    private User getCurrentUser()
    {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

    }
}
