package com.exfantasy.template.security.listener;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import com.exfantasy.template.security.service.LoginAttemptService;
import com.exfantasy.template.services.user.UserService;

/**
 * <pre>
 * Spring Security 驗證成功 Listener
 * </pre>
 * 
 * @author tommy.feng
 *
 */
@Component
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    private LoginAttemptService loginAttemptService;
    
    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent e) {
    	Authentication auth = e.getAuthentication();
    	User user = (User) auth.getPrincipal();
        WebAuthenticationDetails authDetails = (WebAuthenticationDetails) auth.getDetails();
        
        // update LoginAttemptService
        loginAttemptService.loginSucceeded(authDetails.getRemoteAddress());
        
        // update db info
        String email = user.getUsername();
        com.exfantasy.template.mybatis.model.User myUser = userService.queryUserByEmail(email);
        
        myUser.setLastSigninTime(new Date(System.currentTimeMillis()));
        userService.updateUserSelective(myUser);
    }
}
