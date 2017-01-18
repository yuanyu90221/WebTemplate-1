package com.exfantasy.template.security.authentication;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * 當 Spring Security 登入失敗後在此處理 
 * </pre>
 *
 * @author tommy.feng
 *
 */
@Component
public class MyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
    	
    	// 參考: http://stackoverflow.com/questions/26502958/an-explanation-about-overriding-simpleurlauthenticationfailurehandler
    	if (exception.getClass().isAssignableFrom(DisabledException.class)) {
    		response.sendRedirect("/login?user_disabled");
    	}
    	else {
    		response.sendRedirect("/login?login_failed");
    	}
    }
}
