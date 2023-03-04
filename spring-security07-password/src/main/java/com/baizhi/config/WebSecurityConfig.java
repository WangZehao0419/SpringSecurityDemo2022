package com.baizhi.config;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .anyRequest().authenticated();
//        http.formLogin();
//    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

//    private final MyUserDetailServiceImpl userDetailService;
//
//    @Autowired
//    public WebSecurityConfig(MyUserDetailServiceImpl userDetailService) {
//        this.userDetailService = userDetailService;
//    }
//
//
//    @Override
////    @Bean
//    protected UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
//        inMemoryUserDetailsManager.createUser(User.withUsername("root").password("$2a$10$MMZa3hqLw0ktb1vrkzzxNOqa72k2p3ZHFyjBvFGwAgSYJfaXu7V7S").roles("admin").build());
//        return inMemoryUserDetailsManager;
//    }


//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService());
//    }

}
