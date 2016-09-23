package com.exfantasy.template.mybatis.model;

import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

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

    @ApiModelProperty(notes = "", required = true)
    private boolean enabled;

    @ApiModelProperty(notes = "建立時間", required = true)
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

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        User other = (User) that;
        return (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getMobileNo() == null ? other.getMobileNo() == null : this.getMobileNo().equals(other.getMobileNo()))
            && (this.getLineId() == null ? other.getLineId() == null : this.getLineId().equals(other.getLineId()))
            && (this.isEnabled() == other.isEnabled())
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getLastSigninTime() == null ? other.getLastSigninTime() == null : this.getLastSigninTime().equals(other.getLastSigninTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getMobileNo() == null) ? 0 : getMobileNo().hashCode());
        result = prime * result + ((getLineId() == null) ? 0 : getLineId().hashCode());
        result = prime * result + (isEnabled() ? 1231 : 1237);
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getLastSigninTime() == null) ? 0 : getLastSigninTime().hashCode());
        return result;
    }
}