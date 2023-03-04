package com.baizhi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final FindByIndexNameSessionRepository sessionRepository;

    @Autowired
    public SecurityConfig(FindByIndexNameSessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Bean
    public SpringSessionBackedSessionRegistry sessionRegistry() {
        return new SpringSessionBackedSessionRegistry(sessionRepository);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated();
        http.formLogin();
        http.csrf().disable();
        http.sessionManagement()
                .maximumSessions(1)
                .expiredSessionStrategy(event -> {

                    Map<String, Object> result = new HashMap<>();
                    result.put("status", 500);
                    result.put("msg","当前会话已经失效，请重新登录 !");

                    String s = new ObjectMapper().writeValueAsString(result);

                    HttpServletResponse response = event.getResponse();
                    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                    response.getWriter().println(s);
                    response.flushBuffer();
                })
                .maxSessionsPreventsLogin(true)
                .sessionRegistry(sessionRegistry())
        ;

    }



/*    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }*/
}
