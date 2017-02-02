package com.exfantasy.template.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.exfantasy.template.security.authentication.MyAuthenticationFailureHandler;
import com.exfantasy.template.security.authentication.MyAuthenticationProvider;
import com.exfantasy.template.security.service.MyUserDetailsService;

/**
 * <pre>
 * Spring Security 設定
 * 
 * 參考:
 * 	<a href="https://spring.io/guides/gs/securing-web/">Spring Security 範例</a>
 * 	<a href="http://stackoverflow.com/questions/29643183/spring-security-preauthorize-not-working">允許 method 權限管控方式</a>
 * </pre>
 * 
 * @author tommy.feng
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
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
            	.antMatchers("/", "/home").permitAll() // 允許未經過登入可存取的路徑
            	.antMatchers("/test/**").permitAll() // 允許未經過登入測試程式
            	.antMatchers("/register*").permitAll() // 允許未經過登入存取註冊頁面, 且網址後面可帶參數
            	.antMatchers("/user/do_register").permitAll() // 允許未經過登入透過 api 註冊
            	.antMatchers("/user/forgot_password").permitAll() // 允許未經過登入可透過 api 處理忘記密碼
            	.antMatchers("/login*").permitAll() // 允許未經過登入存取登入頁面, 且網址後面可帶參數
            	.anyRequest().authenticated() // 除了上面, 輸入任何如果沒有登入, 都會先被導到 login
            .and()
            	.formLogin()
            	.loginPage("/login").permitAll() // 這邊 permitAll 不能拿掉, 會有問題!!
            	.usernameParameter("email")
            	.passwordParameter("password")
            	.defaultSuccessUrl("/main", true) // 登入成功後導向
            	.failureHandler(myAuthenticationFailureHandler)
//            	.failureUrl("/login?login_failed")
            .and()
            	.logout().permitAll() // 這邊 permitAll 不能拿掉, 會有問題!!
            	.logoutSuccessUrl("/login?logout") // 登出成功後導回 login 頁面並帶參數 logout
            .and()
            	.csrf().disable();
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
    	web
    		.ignoring()
    		.antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security", "/swagger-ui.html", "/webjars/**") // http://stackoverflow.com/questions/37671125/how-to-configure-spring-security-to-allow-swagger-url-to-be-accessed-without-aut
    		.antMatchers("/css/**", "/images/**", "/js/**", "/fonts/**"); // 讓這些 static content 可以被讀取, PS: 一定要兩個 *, 因為有子目錄
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
