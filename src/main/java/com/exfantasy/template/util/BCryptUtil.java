package com.exfantasy.template.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * <pre>
 * 加密及驗證工具
 * </pre>
 * 
 * @author tommy.feng
 *
 */
public class BCryptUtil {
	private static final int workload = 8;

	/**
	 * 將明碼編碼
	 * 
	 * @param rawPassword 明碼
	 * @return 明碼經過編碼
	 */
	public static String encrypt(String rawPassword) {
		String salt = BCrypt.gensalt(workload);
		String encodedPassword = BCrypt.hashpw(rawPassword, salt);
		return encodedPassword;
	}

	/**
	 * 確認明碼與編碼相符
	 * 
	 * @param rawPassword
	 * @param encodedPassword
	 * @return
	 */
	public static boolean isPasswordMatched(String rawPassword, String encodedPassword) {
		return BCrypt.checkpw(rawPassword, encodedPassword);
	}
}