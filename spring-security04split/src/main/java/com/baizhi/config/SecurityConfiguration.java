package com.baizhi.config;

import com.baizhi.service.MyUserDetailServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter();
        // 指定 认证url
        loginFilter.setFilterProcessesUrl("/doLogin");
        // 指定 用户名key
        loginFilter.setUsernameParameter("uname");
        // 指定 密码key
        loginFilter.setPasswordParameter("passwd");
        // 指定认证管理器
        loginFilter.setAuthenticationManager(authenticationManagerBean());
        // 认证成功处理
        loginFilter.setAuthenticationSuccessHandler((request, response, authentication) -> {
            Map<String, Object> result = new HashMap<>();
            result.put("msg", "登录成功");
            result.put("status", 200);
            result.put("authentication", authentication);

            String str = new ObjectMapper().writeValueAsString(result);

            response.setStatus(HttpStatus.OK.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(str);
        });
        // 认证失败处理
        loginFilter.setAuthenticationFailureHandler((request, response, exception) -> {
            Map<String, Object> result = new HashMap<>();
            result.put("msg", "登录失败" + exception);
            result.put("code", 500);

            String s = new ObjectMapper().writeValueAsString(result);

            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(s);
        });
        return loginFilter;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

/*
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        inMemoryUserDetailsManager.createUser(User.withUsername("root").password("{noop}123").roles("admin").build());
        return inMemoryUserDetailsManager;
    }
*/

    private final MyUserDetailServiceImpl userDetailService;
    @Autowired
    public SecurityConfiguration(MyUserDetailServiceImpl userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService());
        auth.userDetailsService(userDetailService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated();
        http.formLogin();
        http.logout()
                .logoutUrl("/doLogout")
                .logoutRequestMatcher(new OrRequestMatcher(
                        new AntPathRequestMatcher("/doLogout", HttpMethod.DELETE.name()),
                        new AntPathRequestMatcher("/doLogout", HttpMethod.POST.name())
                ))
                .logoutSuccessHandler((request, response, authentication) -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("msg", "注销成功，当前认证对象为:" + authentication);
                    result.put("status", 200);

                    String s = new ObjectMapper().writeValueAsString(result);

                    response.setStatus(HttpStatus.OK.value());
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().println(s);
                });
        http.csrf().disable();

        // at: 用来某个filter 替换过滤器链中哪个filter
        // before: 放在过滤器链中哪个filter 之前
        // after: 放在过滤器链中那个filter 之后
        http.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);

        http.exceptionHandling()
                // 未认证【未登录】
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.getWriter().println("请认证之后再去处理！");
                });
    }
}
