package com.exfantasy.template.services.user;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.exfantasy.template.cnst.CloudStorage;
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
import com.exfantasy.template.services.file.FileService;
import com.exfantasy.template.vo.request.RegisterVo;

/**
 * <pre>
 * 處理使用者相關邏輯
 * 
 * Cache 機制使用參考:
 * <a href="https://spring.io/guides/gs/caching/">Caching Data with Spring</a>
 * <a href="http://docs.spring.io/spring/docs/current/spring-framework-reference/html/cache.html#cache-spel-context">Cache Document</a>
 * <a href="http://javasampleapproach.com/spring-framework/cache-data-spring-cache-using-spring-boot">Spring Boot Cache Example</a>
 *  
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
    
    @Autowired
    private FileService fileService;
    
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
    @Cacheable(cacheNames = "users", key = "#email")
	public User queryUserByEmail(String email) {
    	logger.info(">>>>> In UserService queryUserByEmail");
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
    @Cacheable(cacheNames = "userRolesByEmail", key = "#email")
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
    @Cacheable(cacheNames = "userRolesById", key = "#user.getUserId()")
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
     * 上傳大頭照到雲端空間
     * </pre>
     * 
     * @param multipartFile 欲上傳的大頭照
     * 
     * @return {@link CloudStorage} 最後儲存的雲端空間
     */
	public CloudStorage uploadProfileImage(MultipartFile multipartFile) {
		CloudStorage cloudStorage = fileService.uploadProfileImage(multipartFile);
		return cloudStorage;
	}
	
	/**
	 * <pre>
	 * 從雲端空間刪除大頭照
	 * </pre>
	 * 
	 * @return {@link CloudStorage} 最後儲存的雲端空間
	 */
	public CloudStorage deleteProfileImage() {
		CloudStorage cloudStorage = fileService.deleteProfileImage();
		return cloudStorage;
	}

	/**
	 * <pre>
	 * 從雲端空間取得大頭照
	 * </pre>
	 * 
	 * @return ResponseEntity<byte[]> 大頭照
	 */
	public ResponseEntity<byte[]> getProfileImage() {
		ResponseEntity<byte[]> profileImage = fileService.getProfileImage();
		return profileImage;
	}
}