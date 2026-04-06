package com.finance.dashbaord.dto;

import com.finance.dashbaord.entity.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUserRoleRequest {
    @NotNull
    private Role role;

}
