package com.exfantasy.template.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.exfantasy.template.util.BCryptUtil;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestEncryptUtil {
	
	@Test
	public void testPassword() {
		String rawPassword = "b9134034";
		String encodedPassword = BCryptUtil.encrypt(rawPassword);
		
		System.out.println("Raw password: [" + rawPassword + "] -> encoded password: [" + encodedPassword + "]");
		
		assertThat(BCryptUtil.isPasswordMatched(rawPassword, encodedPassword)).isTrue();
		
		String wrongRawPassword = "bbbbbbb";
		
		assertThat(BCryptUtil.isPasswordMatched(wrongRawPassword, encodedPassword)).isFalse();
	}
}
