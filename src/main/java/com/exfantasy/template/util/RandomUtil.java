package com.exfantasy.template.util;

import java.util.Random;

import org.apache.commons.lang3.ArrayUtils;

public class RandomUtil {
	// 隨機碼 英文
	private final static char[] codeSequenceEn = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q',
			'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	// 隨機碼 數字
	private final static char[] codeSequenceNo = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	// 隨機碼 全部
	private final static char[] codeSequenceAll = ArrayUtils.addAll(codeSequenceEn, codeSequenceNo);

	public static String getRandomCode(int length) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			buffer.append(getRandomCodeFromSequence());
		}
		return buffer.toString();
	}

	private static String getRandomCodeFromSequence() {
		Random random = new Random();

		char[] codeSequence = codeSequenceAll;

		return String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
	}
}
