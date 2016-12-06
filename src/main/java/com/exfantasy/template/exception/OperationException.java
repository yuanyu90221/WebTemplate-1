package com.exfantasy.template.exception;

import com.exfantasy.template.cnst.ResultCode;

/**
 * <pre>
 * 自訂操作系統產生的例外
 * </pre>
 * 
 * @author tommy.feng
 *
 */
public class OperationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private ResultCode resultCode;
	private String errorMessage;
	
	public OperationException(ResultCode resultCode) {
		this.resultCode = resultCode;
		this.errorMessage = resultCode.getMessage();
	}

	public OperationException(ResultCode resultCode, String errorMessage) {
		this.resultCode = resultCode;
		this.errorMessage = errorMessage;
	}

	public ResultCode getErrorCode() {
		return resultCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
