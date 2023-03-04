package com.baizhi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WangZehao
 * @since 2023/1/13 17:28
 */
@RestController
@RequestMapping("index")
public class IndexController {
    @RequestMapping()
    public String helloSecurity() {
        return "index spring security";
    }
}
