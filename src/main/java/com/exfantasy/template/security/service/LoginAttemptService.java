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
 * <pre>
 * 處理多次嘗試登入
 * 
 * 參考: 
 * 	<a href="http://genchilu-blog.logdown.com/posts/745182">用 Spring Boot 實作預防暴力登入嘗試的機制</a>
 * </pre>
 * 
 * @author tommy.feng
 *
 */
@Service
public class LoginAttemptService {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginAttemptService.class);
	/**
	 * 客製化設定
	 */
    private CustomConfig customConfig;
	
    private LoadingCache<String, Integer> blockList;

    /**
     * <pre>
     * 建構子如何將物件綁入
     * 
     * 參考:
     * 	<a href="http://geekabyte.blogspot.tw/2014/06/how-to-autowire-bean-with-constructor.html">如何綁定物件</a>
     * </pre>
     * 
     * @param customConfig 客製化設定
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

    /**
     * <pre>
     * 登入成功
     * </pre>
     * 
     * @param ip 嘗試登入 IP
     */
    public void loginSucceeded(String ip) {
        blockList.invalidate(ip);
    }

    /**
     * <pre>
     * 登入失敗
     * </pre>
     * 
     * @param ip 嘗試登入 IP
     */
    public void loginFailed(String ip) {
        int attempts = 0;
        try {
            attempts = blockList.get(ip);
        } catch (ExecutionException e) {
            attempts = 0;
        }
        attempts++;
        blockList.put(ip, attempts);
        
        logger.warn("Increase [{}] login failed count to: [{}]", ip, attempts);
    }

    /**
     * <pre>
     * IP 是否被阻擋
     * </pre>
     * 
     * @param ip 嘗試登入 IP
     * @return 是否被阻擋
     */
    public boolean isBlocked(String ip) {
        try {
            Integer totalLoginFailed = blockList.get(ip);
            
            boolean isBlocked = totalLoginFailed >= customConfig.getLoginMaxAttempt(); 
            if (isBlocked) {
            	logger.warn("Block [{}] login failed count: [{}]", ip, totalLoginFailed);
            }
			return isBlocked;
        } catch (ExecutionException e) {
            return false;
        }
    }
}
