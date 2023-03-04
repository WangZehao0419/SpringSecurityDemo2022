package com.baizhi.config;

import org.springframework.security.core.context.SecurityContextHolder;

//@Configuration
public class SecurityContextHolderConfig {

    static {
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }
}
