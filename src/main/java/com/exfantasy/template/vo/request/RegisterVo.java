package com.exfantasy.template.vo.request;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.exfantasy.template.constraint.Password;

public class RegisterVo {
	@NotEmpty(message = "Please input email")
	@Email(message = "Invalid email format")
    private String email;
	
	@NotEmpty(message = "Please input mobile number")
	private String mobileNo;
	
	@NotEmpty(message = "Please input line id")
	private String lineId;

	@NotEmpty(message = "Please input password")
	@Password(message = "Invalid password")
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
