package com.finance.dashbaord.reports.service;

import com.finance.dashbaord.entity.FinancialRecord;
import com.finance.dashbaord.reports.dto.*;
import com.finance.dashbaord.reports.spec.RecordSpecification;
import com.finance.dashbaord.repository.FinancialRecordReposiotry;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final FinancialRecordReposiotry financialRecordReposiotry;

    // 🔥 GET CURRENT USER
    private String getCurrentUserEmail() {
        return SecurityContextHolder.getContext()
                .getAuthentication().getName();
    }

    // 🔥 COMMON METHOD (DRY PRINCIPLE)
    private List<FinancialRecord> getFilteredRecords(DashBoardFilterRequest filter) {

        if (filter == null) {
            filter = new DashBoardFilterRequest();
        }

        String email = getCurrentUserEmail();

        return financialRecordReposiotry.findAll(
                RecordSpecification.filter(filter, email)
        );
    }

    //  SUMMARY
    @Cacheable(value = "summary", key = "#root.methodName + #filter.toString() + #root.target.getUser()")
    public DashboardSummaryResponse getSummary(DashBoardFilterRequest filter) {

        List<FinancialRecord> records = getFilteredRecords(filter);

        BigDecimal income = BigDecimal.ZERO;
        BigDecimal expense = BigDecimal.ZERO;

        for (FinancialRecord r : records) {
            if (r.getType().name().equals("INCOME")) {
                income = income.add(r.getAmount());
            } else {
                expense = expense.add(r.getAmount());
            }
        }

        return DashboardSummaryResponse.builder()
                .totalIncome(income)
                .totalExpense(expense)
                .build();
    }

    //  CATEGORY
    @Cacheable(value = "category", key = "#filter.toString() + #root.target.getUser()")
    public List<CategoryResponse> getCategoryBreakdown(DashBoardFilterRequest filter) {

        List<FinancialRecord> records = getFilteredRecords(filter);

        Map<String, BigDecimal> map = new HashMap<>();

        for (FinancialRecord r : records) {
            map.put(
                    r.getCategory(),
                    map.getOrDefault(r.getCategory(), BigDecimal.ZERO)
                            .add(r.getAmount())
            );
        }

        return map.entrySet().stream()
                .map(e -> new CategoryResponse(e.getKey(), e.getValue()))
                .toList();
    }

    //  MONTHLY
    @Cacheable(value = "monthly", key = "#filter.toString() + #root.target.getUser()")
    public List<MonthlyResponse> getMonthlyTrends(DashBoardFilterRequest filter) {

        List<FinancialRecord> records = getFilteredRecords(filter);

        Map<String, BigDecimal> map = new TreeMap<>();

        for (FinancialRecord r : records) {

            String month = r.getDate().toString().substring(0, 7);

            map.put(
                    month,
                    map.getOrDefault(month, BigDecimal.ZERO)
                            .add(r.getAmount())
            );
        }

        return map.entrySet().stream()
                .map(e -> new MonthlyResponse(e.getKey(), e.getValue()))
                .toList();
    }
}