package com.finance.dashbaord.reports.controller;

import com.finance.dashbaord.common.ApiResponse;
import com.finance.dashbaord.reports.dto.CategoryResponse;
import com.finance.dashbaord.reports.dto.DashBoardFilterRequest;
import com.finance.dashbaord.reports.dto.DashboardSummaryResponse;
import com.finance.dashbaord.reports.dto.MonthlyResponse;
import com.finance.dashbaord.reports.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashbaord")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @PreAuthorize("hasAnyRole('ANALYST','ADMIN')")
    @PostMapping("/summary")
    public ResponseEntity<ApiResponse<DashboardSummaryResponse>> summary(
            @RequestBody (required = false)DashBoardFilterRequest filter) {

        return ResponseEntity.ok(
                ApiResponse.<DashboardSummaryResponse>builder()
                        .message("Summary fetched")
                        .data(dashboardService.getSummary(filter))
                        .build()
        );
    }

    @PreAuthorize("hasAnyRole('ANALYST','ADMIN')")
    @PostMapping("/category")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> category(
            @RequestBody DashBoardFilterRequest filter) {

        return ResponseEntity.ok(
                ApiResponse.<List<CategoryResponse>>builder()
                        .message("Category fetched")
                        .data(dashboardService.getCategoryBreakdown(filter))
                        .build()
        );
    }

    @PreAuthorize("hasAnyRole('ANALYST','ADMIN')")
    @PostMapping("/monthly")
    public ResponseEntity<ApiResponse<List<MonthlyResponse>>> monthly(
            @RequestBody DashBoardFilterRequest filter) {

        return ResponseEntity.ok(
                ApiResponse.<List<MonthlyResponse>>builder()
                        .message("Monthly fetched")
                        .data(dashboardService.getMonthlyTrends(filter))
                        .build()
        );
    }
}
