package com.exfantasy.template.util;

import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

/**
 * <pre>
 * 錯誤訊息處理工具 
 * </pre>
 * 
 * @author tommy.feng
 *
 */
public class ErrorMsgUtil {

	/**
	 * <pre>
	 * 當前端傳來的資訊有錯誤時, 組成錯誤訊息
	 * </pre>
	 * 
	 * @param result
	 * @return
	 */
	public static String getErrorMsgs(BindingResult result) {
		StringBuilder buffer = new StringBuilder();
		
		List<ObjectError> allErrors = result.getAllErrors();
		for (int i = 0; i < allErrors.size(); i++) {
			buffer.append(allErrors.get(i).getDefaultMessage());
			if (i != allErrors.size() - 1) {
				buffer.append("\n");
			}
		}
		String errorMsg = buffer.toString();
		return errorMsg;
	}
}
