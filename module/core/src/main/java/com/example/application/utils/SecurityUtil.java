package com.example.application.utils;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static String getAuthenticationName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
