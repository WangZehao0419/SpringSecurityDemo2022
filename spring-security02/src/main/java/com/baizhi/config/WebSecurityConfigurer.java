package com.baizhi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

/**
 * @author WangZehao
 * @since 2023/1/13 17:31
 */
@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
    private final MyUserDetailServiceImpl userDetailService;

    @Autowired
    public WebSecurityConfigurer(MyUserDetailServiceImpl userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService);
    }

    // 作用:用来将自定义AuthenticationManager在工厂中进行暴露，可以在任何位置注入
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .mvcMatchers("/index").permitAll()
                .mvcMatchers("/login.html").permitAll()
                .anyRequest().authenticated()
                .and().formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/dologin")
                .usernameParameter("uname")
                .passwordParameter("passwd")
                .successForwardUrl("/index")
                .defaultSuccessUrl("/index")
                .successHandler(new MyAuthenticationSuccessHandler())
//                .failureUrl("/login.html")
//                .failureForwardUrl("/login.html")
                .failureHandler(new MyAuthenticationFailureHandler())
                .and().logout()
                .logoutUrl("/dologoutPost")
                .logoutRequestMatcher(new OrRequestMatcher(
                        new AntPathRequestMatcher("/dologoutGet", "GET"),
                        new AntPathRequestMatcher("/dologoutPost", "POST")
                ))
                .invalidateHttpSession(true)
                .clearAuthentication(true)
//                .logoutSuccessUrl("/login")
                .logoutSuccessHandler(new MyLogoutSuccessHandler())
//                .and()
//                .csrf().disable()
        ;
    }
}
