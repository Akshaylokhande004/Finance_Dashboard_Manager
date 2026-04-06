package com.finance.dashbaord.reports.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class CategoryResponse {
    private String category;
    private BigDecimal total;
}

