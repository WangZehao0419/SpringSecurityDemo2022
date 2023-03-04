package com.baizhi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.log.LogMessage;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * 自定义记住我 services 实现类
 */
public class MyPersistentTokenBasedRememberMeServices extends PersistentTokenBasedRememberMeServices {
    private boolean alwaysRemember;

    public MyPersistentTokenBasedRememberMeServices(String key, UserDetailsService userDetailsService, PersistentTokenRepository tokenRepository) {
        super(key, userDetailsService, tokenRepository);
    }

    /**
     * 自定义前后端分离 获取 remember-me 的方式
     * @param request the request submitted from an interactive login, which may include
     * additional information indicating that a persistent login is desired.
     * @param parameter the configured remember-me parameter name.
     * @return
     */
    @Override
    protected boolean rememberMeRequested(HttpServletRequest request, String parameter) {
        if (this.alwaysRemember) {
            return true;
        }
        try {
            Map<String, String> loginInfo = new ObjectMapper().readValue(request.getInputStream(), Map.class);
            String paramValue = loginInfo.get(parameter);
            if (paramValue != null) {
                if (paramValue.equalsIgnoreCase("true") || paramValue.equalsIgnoreCase("on")
                        || paramValue.equalsIgnoreCase("yes") || paramValue.equals("1")) {
                    return true;
                }
            }
            this.logger.debug(
                    LogMessage.format("Did not send remember-me cookie (principal did not set parameter '%s')", parameter));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void setAlwaysRemember(boolean alwaysRemember) {
        this.alwaysRemember = alwaysRemember;
    }

    public boolean isAlwaysRemember() {
        return alwaysRemember;
    }
}
