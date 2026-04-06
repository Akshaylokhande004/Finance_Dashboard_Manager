package com.finance.dashbaord.service;

import com.finance.dashbaord.dto.UpdateUserRoleRequest;
import com.finance.dashbaord.entity.User;
import com.finance.dashbaord.exception.ResourceNotFoundException;
import com.finance.dashbaord.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    public void updateUserRole(String email, UpdateUserRoleRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setRole(request.getRole());

        userRepository.save(user);
    }
}
