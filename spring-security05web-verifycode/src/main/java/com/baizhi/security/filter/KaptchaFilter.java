package com.baizhi.security.filter;

import com.baizhi.security.exception.KaptchaNotMatchException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class KaptchaFilter extends UsernamePasswordAuthenticationFilter {

    private static final String FORM_KAPTCHA_KEY = "kaptcha";
    private String kaptchaParameter = FORM_KAPTCHA_KEY;

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
        // 获取用户输入的验证码
        String verifyCode = request.getParameter(getKaptchaParameter());
        // 获取系统生成的验证码
        String kaptcha = (String) request.getSession().getAttribute("kaptcha");
        // 比对验证码
        if (!ObjectUtils.isEmpty(verifyCode) && !ObjectUtils.isEmpty(kaptcha)
                && verifyCode.equalsIgnoreCase(kaptcha)) {
            return super.attemptAuthentication(request, response);
        }
        throw new KaptchaNotMatchException("验证码不匹配！");
    }
}
