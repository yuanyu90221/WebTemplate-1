package com.exfantasy.template.vo.request;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 活動留言
 * </pre>
 * 
 * @author tommy.feng
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityMessageVo {
	
	@NotNull(message = "Please input activity id")
	@ApiModelProperty(notes = "活動 ID", required = true)
	private Integer activityId;

	@NotNull(message = "Please input message")
	@ApiModelProperty(notes = "留言", required = true)
    private String message;

}
