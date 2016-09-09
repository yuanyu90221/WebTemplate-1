package com.exfantasy.template.mybatis.model;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

public class User {
	@ApiModelProperty(notes = "使用者 ID", required = true)
    private Integer userId;

	@ApiModelProperty(notes = "使用者註冊時使用的 email", required = true)
    private String email;

	@ApiModelProperty(notes = "使用者密碼", required = true)
    private String password;

	@ApiModelProperty(notes = "使用者手機號碼", required = true)
    private String mobileNo;

	@ApiModelProperty(notes = "使用者 LINE ID", required = true)
    private String lineId;

	@ApiModelProperty(notes = "帳號是否有效", required = true)
    private boolean enabled;

	@ApiModelProperty(notes = "帳號建立時間", required = true)
    private Date createTime;

	@ApiModelProperty(notes = "上次登入時間", required = true)
    private Date lastSigninTime;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo == null ? null : mobileNo.trim();
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId == null ? null : lineId.trim();
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastSigninTime() {
        return lastSigninTime;
    }

    public void setLastSigninTime(Date lastSigninTime) {
        this.lastSigninTime = lastSigninTime;
    }
}