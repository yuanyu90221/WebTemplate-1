package com.exfantasy.template.constant;

/**
 * <pre>
 * 定義操作結果代碼
 * </pre>
 * 
 * @author tommy.feng
 *
 */
public enum ResultCode {

	/** 成功 */
	SUCCESS(0, "Succeed"),
	/** 格式錯誤 */
	INVALID_FORMAT(1001, "Invalid format"),
	/** 無權限操作 */
	ACCESS_DENIED(4444, ""),
	/** 系統發生錯誤 */
	SYSTEM_EXCEPTION(9999, "");
	
	private final int code;
	private final String message;
	
	private ResultCode(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public String getMessage() {
		return this.message;
	}
}
