package com.exfantasy.template.vo.request;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.exfantasy.template.constraint.Password;

import io.swagger.annotations.ApiModelProperty;

/**
 * <pre>
 * 接收前端發送註冊資料用
 * </pre>
 * 
 * @author tommy.feng
 *
 */
public class RegisterVo {
	@NotEmpty(message = "Please input email")
	@Email(message = "Invalid email format")
	@ApiModelProperty(notes = "使用者的系統帳號, 這邊使用 email", required = true)
    private String email;
	
	@NotEmpty(message = "Please input mobile number")
	@ApiModelProperty(notes = "使用者的手機號碼", required = true)
	private String mobileNo;
	
	@NotEmpty(message = "Please input line id")
	@ApiModelProperty(notes = "使用者的 LINE ID", required = true)
	private String lineId;

	@NotEmpty(message = "Please input password")
	@Password(message = "Invalid password")
	@ApiModelProperty(notes = "使用者的密碼", required = true)
    private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
