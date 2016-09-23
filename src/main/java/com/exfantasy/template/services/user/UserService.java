package com.exfantasy.template.services.user;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.exfantasy.template.config.CustomConfig;
import com.exfantasy.template.constant.ResultCode;
import com.exfantasy.template.constant.Role;
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

//    private final CommonConfig adminConfig;
//
//    @Autowired
//    public UserService(CommonConfig commonConfig) {
//        this.commonConfig = commonConfig;
//    }

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
    
	public User queryUserByEmail(String email) {
		UserExample example = new UserExample();
		example.createCriteria().andEmailEqualTo(email);
		List<User> user = userMapper.selectByExample(example);
		return user.isEmpty() ? null : user.get(0);
	}
	
	public List<UserRole> queryUserRoles(User user) {
		UserRoleExample example = new UserRoleExample();
		example.createCriteria().andUserIdEqualTo(user.getUserId());
		List<UserRole> roles = userRoleMapper.selectByExample(example);
		return roles.isEmpty() ? null : roles;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public int updateUserSelective(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public int updateUserByReplace(User user) {
        return userMapper.updateByPrimaryKey(user);
    }
    
}
