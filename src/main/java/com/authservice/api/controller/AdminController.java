package com.authservice.api.controller;

import com.authservice.common.response.ApiResponse;
import com.authservice.common.response.Payload;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ApiResponse getUsers() {
        return Payload.success("Testing Admin success");
    }
}
