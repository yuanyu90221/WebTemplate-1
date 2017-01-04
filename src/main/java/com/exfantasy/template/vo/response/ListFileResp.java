package com.exfantasy.template.vo.response;

import java.util.Date;

import com.exfantasy.template.cnst.CloudStorage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ListFileResp {
	@ApiModelProperty(notes = "儲存的雲端空間", required = true)
	private CloudStorage cloudStorage;
	
	@ApiModelProperty(notes = "儲存的路徑", required = true)
	private String pathAndName;
	
	@ApiModelProperty(notes = "檔案大小", required = true)
	private long fileSizeBytes;
	
	@ApiModelProperty(notes = "最後修改時間", required = true)
	private Date lastModified;
}
