package com.exfantasy.template.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * 客製化設定, 此處設定可參照 application.yaml
 * </pre>
 * 
 * @author tommy.feng
 *
 */
@Component
@ConfigurationProperties(prefix = "custom")
public class CustomConfig {
	/**
	 * 系統管理者名單
	 */
	private List<String> admins = new ArrayList<String>();
	/**
	 * 最高允許嘗試登入次數
	 */
	private int loginMaxAttempt;
	/**
	 * 鎖定 IP 時間(分鐘)
	 */
	private int blockTimeMins;
	
	public void setAdmins(List<String> admins) {
    	this.admins = admins;
    }

    public List<String> getAdmins() {
        return this.admins;
    }
    
    public boolean isAdminEmail(String email) {
		List<String> admins = getAdmins();
		return admins.contains(email);
	}

	public void setLoginMaxAttempt(int loginMaxAttempt) {
    	this.loginMaxAttempt = loginMaxAttempt;
    }
    
    public int getLoginMaxAttempt() {
    	return this.loginMaxAttempt;
    }
    
    public void setBlockTimeMins(int blockTimeMins) {
    	this.blockTimeMins = blockTimeMins;
    }
    
    public int getBlockTimeMins() {
    	return this.blockTimeMins;
    }
}
