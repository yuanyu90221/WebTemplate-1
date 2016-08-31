package com.exfantasy.template.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.exfantasy.template.security.authentication.MyAuthenticationFailureHandler;
import com.exfantasy.template.security.authentication.MyAuthenticationProvider;
import com.exfantasy.template.security.service.MyUserDetailsService;

/**
 * Ref. https://spring.io/guides/gs/securing-web/
 * 
 * @author tommy.feng
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private MyAuthenticationProvider myAuthenticationProvider;

    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        	.authorizeRequests()
            	.antMatchers("/", "/home", "/examples","/main").permitAll() // 允許未經過登入可存取的路徑
            	.antMatchers("/css/**", "/images/**", "/js/**", "/fonts/**").permitAll() // 讓這些 static content 可以被讀取, PS: 一定要兩個 *, 因為有子目錄
            	.antMatchers("/register").permitAll() // 允許未經過登入存取註冊頁面
            	.antMatchers("/user/do_register").permitAll() // 允許未經過登入透過 api 註冊
            	.anyRequest().authenticated() // 除了上面, 輸入任何如果沒有登入, 都會先被導到 login
            	.and()
            .formLogin()
            	.loginPage("/login").permitAll()
            	//.defaultSuccessUrl("/hello", true) // 登入成功後導向
            	.defaultSuccessUrl("/main",true)
            	.failureHandler(myAuthenticationFailureHandler) // 登入失敗處理
            	.and()
            .logout().permitAll()
            	.and()
            .csrf().disable();
    }

    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return myUserDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService);
        auth.authenticationProvider(myAuthenticationProvider);
    }
}
