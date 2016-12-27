package com.exfantasy.template.services.user;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.exfantasy.template.cnst.ResultCode;
import com.exfantasy.template.cnst.Role;
import com.exfantasy.template.config.CustomConfig;
import com.exfantasy.template.exception.OperationException;
import com.exfantasy.template.mybatis.mapper.UserMapper;
import com.exfantasy.template.mybatis.mapper.UserRoleMapper;
import com.exfantasy.template.mybatis.model.User;
import com.exfantasy.template.mybatis.model.UserExample;
import com.exfantasy.template.mybatis.model.UserRole;
import com.exfantasy.template.mybatis.model.UserRoleExample;
import com.exfantasy.template.security.password.Password;
import com.exfantasy.template.vo.request.RegisterVo;

/**
 * <pre>
 * 處理使用者相關邏輯 
 * </pre>
 * 
 * @author tommy.feng
 *
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private UserRoleMapper userRoleMapper;
    
    @Autowired
    private CustomConfig customConfig;

    /**
     * <pre>
     * 註冊新的使用者
     * </pre>
     * 
     * @param registerVo
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public void register(RegisterVo registerVo) {
    	User existedUser = queryUserByEmail(registerVo.getEmail());
    	if (existedUser != null) {
    		logger.warn("----- Email already in used: <{}> -----", registerVo.getEmail());
    		throw new OperationException(ResultCode.EMAIL_ALREADY_IN_USED);
    	}
    	
    	User user = new User();
    	user.setEmail(registerVo.getEmail());
    	user.setPassword(Password.encrypt(registerVo.getPassword()));
    	user.setMobileNo(registerVo.getMobileNo());
    	user.setLineId(registerVo.getLineId());
    	user.setEnabled(true);
    	user.setCreateTime(new Date(System.currentTimeMillis()));
        userMapper.insert(user);
        
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getUserId());
        userRole.setRole(customConfig.isAdminEmail(registerVo.getEmail()) ? Role.ADMIN : Role.USER);
		userRoleMapper.insert(userRole);
		
		logger.info("----- User register with email: <{}> succeed -----", registerVo.getEmail());
    }
    
    /**
     * <pre>
     * 根據傳入的 email 查詢對應使用者
     * </pre>
     * 
     * @param email
     * @return
     */
	public User queryUserByEmail(String email) {
		UserExample example = new UserExample();
		example.createCriteria().andEmailEqualTo(email);
		List<User> user = userMapper.selectByExample(example);
		return user.isEmpty() ? null : user.get(0);
	}
	
	/**
	 * <pre>
	 * 查詢使用者所擁有的角色
	 * </pre>
	 * 
	 * @param email
	 * @return
	 */
	public List<UserRole> queryUserRolesByEmail(String email) {
		User user = queryUserByEmail(email);
		if (user == null) {
			return null;
		}
		return queryUserRoles(user);
	}
	
	/**
	 * <pre>
	 * 查詢使用者所擁有的角色
	 * </pre>
	 * 
	 * @param user
	 * @return
	 */
	public List<UserRole> queryUserRoles(User user) {
		UserRoleExample example = new UserRoleExample();
		example.createCriteria().andUserIdEqualTo(user.getUserId());
		List<UserRole> roles = userRoleMapper.selectByExample(example);
		return roles.isEmpty() ? null : roles;
	}

	/**
	 * <pre>
	 * 根據 Primary Key 更新使用者, 有欄位變動才更新
	 * </pre>
	 * 
	 * @param user
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public int updateUserSelective(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }
    
	/**
	 * <pre>
	 * 根據 Primary Key 更新使用者
	 * </pre>
	 * 
	 * @param user
	 * @return
	 */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public int updateUserByReplace(User user) {
        return userMapper.updateByPrimaryKey(user);
    }
    
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
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User user = queryUserByEmail(email);
		return user;
    }
    
}