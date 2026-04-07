package com.finance.dashbaord.repository;

import com.finance.dashbaord.entity.FinancialRecord;
import com.finance.dashbaord.entity.RecordType;
import com.finance.dashbaord.reports.dto.MonthlyResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import com.finance.dashbaord.reports.dto.*;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


import java.time.LocalDate;
import java.util.List;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface FinancialRecordReposiotry extends JpaRepository<FinancialRecord, UUID>, JpaSpecificationExecutor<FinancialRecord>{

    // SUMMARY
    @Query("""
        SELECT 
            COALESCE(SUM(CASE WHEN r.type = 'INCOME' THEN r.amount ELSE 0 END), 0),
            COALESCE(SUM(CASE WHEN r.type = 'EXPENSE' THEN r.amount ELSE 0 END), 0)
        FROM FinancialRecord r
        WHERE r.user.email = :email
        AND r.isDeleted = false
        AND (:startDate IS NULL OR r.date >= :startDate)
        AND (:endDate IS NULL OR r.date <= :endDate)
        AND (:type IS NULL OR r.type = :type)
        AND (:category IS NULL OR r.category = :category)
    """)
    Object[] getSummary(
            @Param("email") String email,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("type") RecordType type,
            @Param("category") String category
    );


    //  CATEGORY
    @Query("""
        SELECT new com.finance.dashbaord.reports.dto.CategoryResponse(
            r.category,
            SUM(r.amount)
        )
        FROM FinancialRecord r
        WHERE r.user.email = :email
        AND r.isDeleted = false
        AND (:startDate IS NULL OR r.date >= :startDate)
        AND (:endDate IS NULL OR r.date <= :endDate)
        AND (:type IS NULL OR r.type = :type)
        AND (:category IS NULL OR r.category = :category)
        GROUP BY r.category
    """)
    List<CategoryResponse> getCategory(
            String email,
            LocalDate startDate,
            LocalDate endDate,
            RecordType type,
            String category
    );


//    //  MONTHLY
//    @Query("""
//        SELECT new com.finance.dashbaord.reports.dto.MonthlyResponse(
//            FUNCTION('TO_CHAR', r.date, 'YYYY-MM'),
//            SUM(r.amount)
//        )
//        FROM FinancialRecord r
//        WHERE r.user.email = :email
//        AND r.isDeleted = false
//        AND (:startDate IS NULL OR r.date >= :startDate)
//        AND (:endDate IS NULL OR r.date <= :endDate)
//        AND (:type IS NULL OR r.type = :type)
//        AND (:category IS NULL OR r.category = :category)
//        GROUP BY FUNCTION('TO_CHAR', r.date, 'YYYY-MM')
//        ORDER BY FUNCTION('TO_CHAR', r.date, 'YYYY-MM')
//    """)
//    List<MonthlyResponse> getMonthly(
//            String email,
//            LocalDate startDate,
//            LocalDate endDate,
//            RecordType type,
//            String category
//    );
  }
