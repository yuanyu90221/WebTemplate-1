package com.exfantasy.template.vo.response;

import com.exfantasy.template.cnst.ResultCode;
import com.exfantasy.template.exception.OperationException;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <pre>
 * 統一回應前端格式 
 * </pre>
 * 
 * @author tommy.feng
 *
 */
@ApiModel(description = "操作回應")
public class RespCommon {
	@ApiModelProperty(notes = "操作代碼", required = true)
	private ResultCode resultCode;
	
	@ApiModelProperty(notes = "回應資料", required = true)
	private Object data;

	public RespCommon(ResultCode resultCode, Object data) {
		this.resultCode = resultCode;
		this.data = data;
	}
	
	public RespCommon(OperationException ex) {
		this.resultCode = ex.getErrorCode();
		this.data = ex.getErrorMessage();
	}

	public int getResultCode() {
		return resultCode.getCode();
	}

	public Object getData() {
		return data;
	}
}