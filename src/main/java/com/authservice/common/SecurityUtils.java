package com.authservice.common;

import com.authservice.infrastructure.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            if (customUserDetails != null) {
                return customUserDetails.getUserId();
            }
        }
        return null;
    }
}
