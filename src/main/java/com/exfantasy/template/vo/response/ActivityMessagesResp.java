package com.exfantasy.template.vo.response;

import java.util.Date;

import lombok.Data;

@Data
public class ActivityMessagesResp {
	private String createUserEmail;
	private Date createDatetime;
	private String msg;
}
