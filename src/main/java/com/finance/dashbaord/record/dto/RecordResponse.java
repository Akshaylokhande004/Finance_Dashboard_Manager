package com.finance.dashbaord.record.dto;

import com.finance.dashbaord.entity.RecordType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class RecordResponse {
    private UUID id;
    private BigDecimal amount;
    private RecordType type;
    private String category;
    private LocalDate date;
    private String note;
}
