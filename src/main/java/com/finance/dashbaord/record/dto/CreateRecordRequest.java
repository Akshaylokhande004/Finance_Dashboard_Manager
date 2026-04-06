package com.finance.dashbaord.record.dto;

import com.finance.dashbaord.entity.RecordType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CreateRecordRequest {
    @NotNull
    @NotNull
    @Positive
    private BigDecimal amount;

    @NotNull
    private RecordType type;

    private String category;

    @NotNull
    private LocalDate date;

    private String note;
}
