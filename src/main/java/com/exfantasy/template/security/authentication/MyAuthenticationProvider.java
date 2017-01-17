package com.exfantasy.template.security.authentication;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import com.exfantasy.template.security.password.Password;
import com.exfantasy.template.security.service.LoginAttemptService;
import com.exfantasy.template.security.service.MyUserDetailsService;

/**
 * <pre>
 * 自訂 Spring Security 檢核 
 * </pre>
 *
 * @author tommy.feng
 *
 */
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {
	
	private static final Logger logger = LoggerFactory.getLogger(MyAuthenticationProvider.class);

	@Autowired
	private MyUserDetailsService myUserDetailsService;

	@Autowired
	private LoginAttemptService loginAttemptService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		WebAuthenticationDetails wad = (WebAuthenticationDetails) authentication.getDetails();
		String userIPAddress = wad.getRemoteAddress();
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();
		if (loginAttemptService.isBlocked(userIPAddress)) {
			logger.warn("~~~~~ This ip has been blocked: <{}> ~~~~~", userIPAddress);
			throw new LockedException("This ip has been blocked");
		}
		UserDetails user = myUserDetailsService.loadUserByUsername(username);
		if (user == null) {
			logger.warn("~~~~~ Cannot find mapping user by email: <{}> ~~~~~", username);
			throw new BadCredentialsException("Username not found.");
		}
		if (!Password.encoder.matches(password, user.getPassword())) {
			logger.warn("~~~~~ Input password: <{}> not match password: <{}> ~~~~~", password, user.getPassword());
			throw new BadCredentialsException("Wrong password.");
		}

		Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
		return new UsernamePasswordAuthenticationToken(user, password, authorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}
}
