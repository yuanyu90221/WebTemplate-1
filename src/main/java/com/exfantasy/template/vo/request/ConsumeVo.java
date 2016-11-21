package com.exfantasy.template.vo.request;

import java.time.LocalDate;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
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
@NoArgsConstructor
public class ConsumeVo {
	@NotEmpty(message = "Please input consume date")
	@ApiModelProperty(notes = "消費日期", required = true)
	private LocalDate consumeDate;
	
	@NotEmpty(message = "Please input type")
	@ApiModelProperty(notes = "分類", required = true)
	private Integer type;
	
	@NotEmpty(message = "Please input product name")
	@ApiModelProperty(notes = "商品名稱", required = true)
	private String prodName;
	
	@NotEmpty(message = "Please input amount")
	@ApiModelProperty(notes = "消費金額", required = true)
	private Integer amount;
	
	@NotEmpty(message = "Please input lottery number")
	@ApiModelProperty(notes = "發票號碼", required = true)
	private String lotteryNo;
	
	@ApiModelProperty(notes = "中獎金額", required = false)
	private Integer prize;
	
	@ApiModelProperty(notes = "是否中獎", required = false)
	private Boolean got;
}
