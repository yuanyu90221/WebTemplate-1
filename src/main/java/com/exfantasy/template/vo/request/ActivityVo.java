package com.exfantasy.template.vo.request;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.exfantasy.template.vo.deserializer.ActivityVoDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 建立活動資料
 * </pre>
 * 
 * @author tommy.feng
 *
 */
@Data
@JsonDeserialize(using = ActivityVoDeserializer.class)
@NoArgsConstructor
@AllArgsConstructor
public class ActivityVo {
	
	@NotNull(message = "Please input title")
	@ApiModelProperty(notes = "活動標題", required = true)
	private String title;
	
	@NotNull(message = "Please input description")
	@ApiModelProperty(notes = "活動描述", required = true)
    private String description;

	@NotNull(message = "Please input activity start date time")
	@ApiModelProperty(notes = "活動開始時間, 格式: yyyy-MM-dd HH:mm:ss", required = true)
    private Date startDatetime;

	@NotNull(message = "Please input latitude")
	@ApiModelProperty(notes = "緯度", required = true)
    private BigDecimal latitude;

	@NotNull(message = "Please input longitude")
	@ApiModelProperty(notes = "經度", required = true)
    private BigDecimal longitude;

	@NotNull(message = "Please input attendee number")
	@ApiModelProperty(notes = "活動預計人數", required = true)
    private Integer attendeeNum;
}
