package com.finance.dashbaord.record.controller;

import com.finance.dashbaord.common.ApiResponse;
import com.finance.dashbaord.record.dto.CreateRecordRequest;
import com.finance.dashbaord.record.dto.RecordResponse;
import com.finance.dashbaord.record.service.RecordService;
import com.finance.dashbaord.repository.RecordRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;



import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/records")
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    /// Create Response API

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<RecordResponse>> create(@Valid @RequestBody CreateRecordRequest createRecordRequest)
    {
       RecordResponse response = recordService.create(createRecordRequest);
       return  ResponseEntity.ok(
               ApiResponse.<RecordResponse>builder()
                       .message("Record created Successfully")
                       .data(response)
                       .build()
       );
    }
    /// Get All Records API with Pagination
    @PreAuthorize("hasAnyRole('VIEWER','ANALYST','ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<RecordResponse>>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<RecordResponse> data = recordService.getAll(pageable);

        return ResponseEntity.ok(
                ApiResponse.<Page<RecordResponse>>builder()
                        .message("Records fetched successfully")
                        .data(data)
                        .build()
        );
    }

    ///  Update API for records
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RecordResponse>> update(
            @PathVariable UUID id,
            @RequestBody @Valid CreateRecordRequest request) {

        RecordResponse updated = recordService.update(id, request);

        return ResponseEntity.ok(
                ApiResponse.<RecordResponse>builder()
                        .message("Record updated successfully")
                        .data(updated)
                        .build()
        );
    }
   /// Soft delete API
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(
            @PathVariable UUID id) {

        recordService.delete(id);

        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .message("Record deleted successfully")
                        .data("Deleted")
                        .build()
        );
    }
}
