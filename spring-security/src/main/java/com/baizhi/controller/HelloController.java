package com.baizhi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WangZehao
 * @since 2023/1/9 14:54
 */
@RestController
@RequestMapping("hello")
public class HelloController {
    @RequestMapping("security")
    public String helloSecurity() {
        return "hello spring security";
    }
}