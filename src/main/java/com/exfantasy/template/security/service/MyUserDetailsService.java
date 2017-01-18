package com.exfantasy.template.security.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.exfantasy.template.mybatis.model.User;
import com.exfantasy.template.mybatis.model.UserRole;
import com.exfantasy.template.services.user.UserService;

/**
 * <pre>
 * Spring Security 需要的 service
 * </pre>
 * 
 * @author tommy.feng
 *
 */
@Service("MyUserDetailsImpl")
public class MyUserDetailsService implements UserDetailsService {
	
	private static final Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);
	
	@Autowired
	private UserService userService;
	
	private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userService.queryUserByEmail(email);
		if (user == null) {
			logger.warn("~~~~~ Cannot find mapping user by email: <{}> ~~~~~", email);
			throw new UsernameNotFoundException("Cannot find user, email: " + email);
		}
		
		List<UserRole> roles = userService.queryUserRoles(user);
		List<GrantedAuthority> gas = createRoles(roles);

		// 轉換成 Spring Security 的 User 去做檢核
		org.springframework.security.core.userdetails.User detailUser 
			= new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, gas);
		
		try {
			detailsChecker.check(detailUser);
		} catch (Exception e) {
			logger.warn("Check account status failed, email: <{}>, isEnabled: <{}>, msg: <{}>", user.getEmail(), user.isEnabled(), e.getMessage());
			throw e;
		}
		
		return detailUser;
	}
	
	private List<GrantedAuthority> createRoles(List<UserRole> roles) {
		List<GrantedAuthority> gas = new ArrayList<GrantedAuthority>();
		for (UserRole role : roles) {
			gas.add(new SimpleGrantedAuthority(role.getRole()));
		}
		return gas;
	}
}