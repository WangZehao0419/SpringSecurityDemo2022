package com.baizhi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    private final Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping("ThreadLocalSecurityContextHolderStrategy")
    public String threadLocalSecurityContextHolderStrategy() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("身份：{}", authentication.getPrincipal());
        logger.info("身份-姓名：{}", ((User) authentication.getPrincipal()).getUsername());
        logger.info("凭证：{}", authentication.getCredentials());
        logger.info("权限：{}", authentication.getAuthorities());
        return "ThreadLocalSecurityContextHolderStrategy";
    }

    @RequestMapping("InheritableThreadLocalSecurityContextHolderStrategy")
    public String inheritableThreadLocalSecurityContextHolderStrategy() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("身份：{}", authentication.getPrincipal());
        logger.info("身份-姓名：{}", ((User) authentication.getPrincipal()).getUsername());
        logger.info("凭证：{}", authentication.getCredentials());
        logger.info("权限：{}", authentication.getAuthorities());
        new Thread(() -> {
            Authentication authenticationThread = SecurityContextHolder.getContext().getAuthentication();
            logger.info("Thread身份：{}", authenticationThread.getPrincipal());
            logger.info("Thread身份-姓名：{}", ((User) authenticationThread.getPrincipal()).getUsername());
            logger.info("Thread凭证：{}", authenticationThread.getCredentials());
            logger.info("Thread权限：{}", authenticationThread.getAuthorities());
        }).start();
        return "InheritableThreadLocalSecurityContextHolderStrategy";
    }
}
