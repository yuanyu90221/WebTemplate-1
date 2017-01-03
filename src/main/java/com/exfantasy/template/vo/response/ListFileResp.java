package com.exfantasy.template.vo.response;

import java.util.Date;

import com.exfantasy.template.cnst.CloudStorage;

import lombok.Data;

@Data
public class ListFileResp {
	private CloudStorage cloudStorage;
	
	private String pathAndName;
	
	private long fileSizeBytes;
	
	private Date lastModified;
}
