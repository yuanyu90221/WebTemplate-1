package com.exfantasy.template.security.service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exfantasy.template.config.CustomConfig;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * Ref. http://genchilu-blog.logdown.com/posts/745182
 * 
 * @author tommy.feng
 *
 */
@Service
public class LoginAttemptService {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginAttemptService.class);

    private CustomConfig customConfig;
	
    private LoadingCache<String, Integer> blockList;

    /**
     * http://geekabyte.blogspot.tw/2014/06/how-to-autowire-bean-with-constructor.html
     * 
     * @param customConfig
     */
    @Autowired
	public LoginAttemptService(CustomConfig customConfig) {
    	this.customConfig = customConfig;
    	
		blockList 
			= CacheBuilder.newBuilder().expireAfterWrite(customConfig.getBlockTimeMins(), TimeUnit.MINUTES)
				.build(new CacheLoader<String, Integer>() {
					public Integer load(String key) {
						return 0;
					}
				});
	}

    public void loginSucceeded(String key) {
        blockList.invalidate(key);
    }

    public void loginFailed(String key) {
        int attempts = 0;
        try {
            attempts = blockList.get(key);
        } catch (ExecutionException e) {
            attempts = 0;
        }
        attempts++;
        blockList.put(key, attempts);
        
        logger.warn("Increase [{}] login failed count to: [{}]", key, attempts);
    }

    public boolean isBlocked(String key) {
        try {
            Integer totalLoginFailed = blockList.get(key);
            
            boolean isBlocked = totalLoginFailed >= customConfig.getLoginMaxAttempt(); 
            if (isBlocked) {
            	logger.warn("Block [{}] login failed count: [{}]", key, totalLoginFailed);
            }
			return isBlocked;
        } catch (ExecutionException e) {
            return false;
        }
    }
}
