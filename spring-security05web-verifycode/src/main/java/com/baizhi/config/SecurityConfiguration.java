package com.baizhi.config;

import com.baizhi.security.filter.KaptchaFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        inMemoryUserDetailsManager.createUser(User.withUsername("root").password("{noop}123").roles("admin").build());
        return inMemoryUserDetailsManager;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/login.html").permitAll()
//                .mvcMatchers("/doLogin").permitAll()
                .mvcMatchers("/verifyCodeImage").permitAll()
                .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/login.html")
//                .loginProcessingUrl("/doLogin")
//                .usernameParameter("uname")
//                .passwordParameter("passwd")
//                .defaultSuccessUrl("/index.html", true)
//                .failureUrl("/login.html")
        ;

        http.logout()
                .logoutUrl("/doLogout")
                .logoutSuccessUrl("/login.html");

        http.addFilterAt(kaptchaFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    public KaptchaFilter kaptchaFilter() throws Exception {
        KaptchaFilter kaptchaFilter = new KaptchaFilter();
        // 指定接收验证码的请求参数名
        kaptchaFilter.setKaptchaParameter("kaptcha");
        // 指定处理登录
        kaptchaFilter.setFilterProcessesUrl("/doLogin");
        kaptchaFilter.setUsernameParameter("uname");
        kaptchaFilter.setPasswordParameter("passwd");
        // 指定认证管理器
        kaptchaFilter.setAuthenticationManager(authenticationManager());
        // 指定认证成功和认证失败
        kaptchaFilter.setAuthenticationSuccessHandler((request, response, authentication) -> {
            response.sendRedirect("/index.html");
        });
        kaptchaFilter.setAuthenticationFailureHandler((request, response, exception) -> {
            response.sendRedirect("/login.html");
        });
        return kaptchaFilter;
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
