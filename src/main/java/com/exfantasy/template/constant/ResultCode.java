package com.exfantasy.template.constant;

/**
 * 定義操作結果代碼
 * 
 * @author tommy.feng
 *
 */
public enum ResultCode {

	/** 成功 */
	SUCCESS(0, "Succeed"),
	/** 格式錯誤 */
	INVALID_FORMAT(1001, "Invalid format"),
	/** 系統發生錯誤 */
	SYSTEM_EXCEPTION(9999, "");
	
	private final int code;
	private final String errorMsg;
	
	private ResultCode(int code, String errorMsg) {
		this.code = code;
		this.errorMsg = errorMsg;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public String getErrorMsg() {
		return this.errorMsg;
	}
}
