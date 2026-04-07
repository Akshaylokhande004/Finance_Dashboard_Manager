package com.finance.dashbaord.reports.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@Getter
@NoArgsConstructor
public class MonthlyResponse {
    private String month;
    private BigDecimal total;
    public MonthlyResponse(String month, BigDecimal total) {
        this.month = month;
        this.total = total;
    }
    public String getMonth() {
        return month;
    }

    public BigDecimal getTotal() {
        return total;
    }

}
