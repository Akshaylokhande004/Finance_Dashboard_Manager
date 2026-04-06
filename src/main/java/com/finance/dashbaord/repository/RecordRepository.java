package com.finance.dashbaord.repository;

import com.finance.dashbaord.entity.FinancialRecord;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.UUID;

public interface RecordRepository extends JpaRepository<FinancialRecord, Long> {
    Page<FinancialRecord> findByIsDeletedFalse(Pageable pageable);
    java.util.Optional<FinancialRecord> findByIdAndIsDeletedFalse(UUID id);
}
