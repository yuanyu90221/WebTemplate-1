package com.exfantasy.template.cnst;

/**
 * <pre>
 * 定義操作結果代碼
 * </pre>
 * 
 * @author tommy.feng
 *
 */
public enum ResultCode {

	/** Code: 0, Message: 成功 */
	SUCCESS(0, "Succeed"),

	/** Code: 1001, Message: 格式錯誤 */
	INVALID_FORMAT(1001, "Invalid format"),
	
	/** Code: 2001, Message: Email 已被使用 */
	EMAIL_ALREADY_IN_USED(2001, "Email already in used"),
	
	/** Code: 2002, Message: 請確認原密碼 */
	PLS_CONFIRM_ORIG_PASSWORD(2002, "Please confirm current password is correct"),
	
	/** Code: 2003, Message: 請確認新密碼不與舊密碼相同 */
	PLS_CONFIRM_NEW_PASSWORD_NOT_SAME_AS_OLD_PASSWORD(2003, "Please confirm the new password not same as old password"),
	
	/** Code: 2004, Message: 找不到對應使用者 */
	CANNOT_FIND_REGISTERATION_INFO(2004, "Cannot find registeration information by email you input"),
	
	/** Code: 3001, Message: 資料重複 */
	DUPLICATE_KEY(3001, "Data already existed"),
	
	/** Code: 4444, Message: 無權限操作 */
	ACCESS_DENIED(4444, ""),
	
	/** Code: 5001, Message: 此活動不存在 */
	ACTIVITY_NOT_EXISTED(5001, "The activity is not existed"),

	/** Code: 5002, Message: 這個活動已經參加 */
	ACTIVITY_ALREADY_JOINED(5002, "The activity already joined"),
	
	/** Code: 8001, Message: 檔案為空 */
	FILE_IS_EMPTY(8001, "File is empty"),
	
	/** Code: 8002, Message: 上傳檔案失敗 */
	UPLOAD_FILE_FAILED(8002, "Upload file failed"),
	
	/** Code: 8003, Message: 刪除檔案失敗 */
	DELETE_FILE_FAILED(8003, "Delete file failed"),
	
	/** Code: 8004, Message: 下載檔案失敗 */
	DOWNLOAD_FILE_FAILED(8004, "Download file failed"),
	
	/** Code: 9001, Message: Amazon S3 服務不允許使用 */
	AMAZON_S3_SERVICE_IS_NOT_AVAILABLE(9001, "Amazon S3 service is not avaiable"),

	/** Code: 9999, Message: 系統發生錯誤 */
	SYSTEM_EXCEPTION(9999, ""),
	;
	
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
	
	public static ResultCode convert(int code) {
		for (ResultCode e : ResultCode.values()) {
			if (e.getCode() == code) {
				return e;
			}
		}
		return null;
	}
}
