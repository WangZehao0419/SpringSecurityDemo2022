package com.baizhi.security.filter;

import com.baizhi.security.exception.KaptchaNotMatchException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class LoginKaptchaFilter extends UsernamePasswordAuthenticationFilter {

    private static final String KAPTCHA_KEY = "kaptcha";

    private String kaptchaParameter = KAPTCHA_KEY;

    public String getKaptchaParameter() {
        return kaptchaParameter;
    }

    public void setKaptchaParameter(String kaptchaParameter) {
        this.kaptchaParameter = kaptchaParameter;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        try {
            // 获取请求数据
            Map<String, String> loginInfo = new ObjectMapper().readValue(request.getInputStream(), Map.class);
            // 获取请求数据中的验证码
            String verifyCode = loginInfo.get(getKaptchaParameter());
            // 获取请求数据的用户名
            String username = loginInfo.get(getUsernameParameter());
            username = (username != null) ? username : "";
            username = username.trim();
            // 获取请求数据的密码
            String password = loginInfo.get(getPasswordParameter());
            password = (password != null) ? password : "";
            // 获取session中的验证码
            String kaptcha = (String) request.getSession().getAttribute("kaptcha");
            // 比对验证码
            if (!ObjectUtils.isEmpty(verifyCode) && !ObjectUtils.isEmpty(kaptcha) && verifyCode.equalsIgnoreCase(kaptcha)) {
                // 用户名和密码认证
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
                setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new KaptchaNotMatchException("验证码不匹配！");
    }
}
