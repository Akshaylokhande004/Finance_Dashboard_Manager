package com.finance.dashbaord.reports.dto;

import com.finance.dashbaord.entity.RecordType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class DashBoardFilterRequest {
    private LocalDate startDate;
    private LocalDate endDate;
    private RecordType type;
    private String category;
}
