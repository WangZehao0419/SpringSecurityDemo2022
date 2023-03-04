package com.baizhi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author WangZehao
 * @since 2023/1/20 14:19
 */
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Map<String, Object> result = new HashMap<>();
        result.put("msg","登录成功");
        result.put("status", 200);
        result.put("authentication", authentication);
        response.setContentType("application/json;charset=UTF-8");

        String str = new ObjectMapper().writeValueAsString(result);
        response.getWriter().println(str);
    }
}
