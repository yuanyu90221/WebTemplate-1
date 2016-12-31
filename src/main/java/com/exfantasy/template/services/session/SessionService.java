package com.exfantasy.template.services.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.exfantasy.template.mybatis.model.User;
import com.exfantasy.template.services.user.UserService;

@Service
public class SessionService {
	
	private static final Logger logger = LoggerFactory.getLogger(SessionService.class);
	
	@Autowired
	private UserService userService;
	
	/**
     * <pre>
     * 取得登入者資訊
     * 
     * <a href="https://www.mkyong.com/spring-security/get-current-logged-in-username-in-spring-security/">從 Spring Security 取得登入者資訊</a>
     * 
     * </pre>
     * 
     * @return
     */
    public User getLoginUser() {
    	logger.info(">>>>> In SessionService getLoginUser()");
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User user = userService.queryUserByEmail(email);
		return user;
    }
}
