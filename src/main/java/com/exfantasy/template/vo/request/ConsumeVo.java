package com.exfantasy.template.vo.request;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.exfantasy.template.vo.deserializer.ConsumeVoDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 記帳資料
 * </pre>
 * 
 * @author tommy.feng
 *
 */
@Data
@JsonDeserialize(using = ConsumeVoDeserializer.class)
@NoArgsConstructor
@AllArgsConstructor
public class ConsumeVo {

	@NotNull(message = "Please input consume date")
	@ApiModelProperty(notes = "消費日期", required = true)
	private LocalDate consumeDate;
	
	@NotNull(message = "Please input type")
	@ApiModelProperty(notes = "分類", required = true)
	private Integer type;
	
	@NotEmpty(message = "Please input product name")
	@ApiModelProperty(notes = "商品名稱", required = true)
	private String prodName;
	
	@NotNull(message = "Please input amount")
	@ApiModelProperty(notes = "消費金額", required = true)
	private Long amount;
	
	@NotEmpty(message = "Please input lottery number")
	@ApiModelProperty(notes = "發票號碼", required = true)
	private String lotteryNo;
}
