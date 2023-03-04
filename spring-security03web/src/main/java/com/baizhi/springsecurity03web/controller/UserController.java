package com.baizhi.springsecurity03web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @RequestMapping("/user")
    public String user() {
        //代码中获取用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        System.out.println("user.getUsername() = " + user.getUsername());
        System.out.println("user.getAuthorities() = " + user.getAuthorities());
        return "user ok";
    }
}
