package com.exfantasy.template.vo.response;

import java.util.Date;

import com.exfantasy.template.cnst.CloudStorage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ListFileResp {
	@ApiModelProperty(notes = "儲存的雲端空間")
	private CloudStorage cloudStorage;
	
	@ApiModelProperty(notes = "儲存的路徑")
	private String pathAndName;
	
	@ApiModelProperty(notes = "檔案大小")
	private long fileSizeBytes;
	
	@ApiModelProperty(notes = "最後修改時間")
	private Date lastModified;
}
