package com.exfantasy.template.vo.response;

import java.util.Date;

import lombok.Data;

@Data
public class ListFileResp {
	private String pathAndName;
	
	private long size;
	
	private Date lastModified;
}
