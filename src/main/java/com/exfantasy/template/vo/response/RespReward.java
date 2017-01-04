package com.exfantasy.template.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RespReward {
	@ApiModelProperty(notes = "期別")
	private String section;
	
	@ApiModelProperty(notes = "獎別")
	private int rewardType;
	
	@ApiModelProperty(notes = "號碼")
	private String no;
}
