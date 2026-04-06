package com.finance.dashbaord.controller;

import com.finance.dashbaord.common.ApiResponse;
import com.finance.dashbaord.dto.UpdateUserRoleRequest;
import com.finance.dashbaord.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // ADMIN ONLY
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{email}/role")
    public ResponseEntity<ApiResponse<String>> updateUserRole(
            @PathVariable String email,
            @RequestBody @Valid UpdateUserRoleRequest request) {

        userService.updateUserRole(email, request);

        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .message("User role updated successfully")
                        .data("Updated")
                        .build()
        );
    }
}
