package com.exfantasy.template.controller;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.exfantasy.template.cnst.ResultCode;
import com.exfantasy.template.cnst.Role;
import com.exfantasy.template.services.mail.MailService;
import com.exfantasy.template.vo.response.ResponseVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <pre>
 * 測試相關 API
 * </pre>
 * 
 * @author tommy.feng
 *
 */
@Controller
@RequestMapping(value = "/test")
@Api("TestController - 測試相關 API")
public class TestController {
	
	@Autowired
	private MailService mailService;
	
	/**
	 * <pre>
	 * 測試權限用
	 * 
	 * 參考:
	 * 	<a href="http://blog.csdn.net/tzdwsy/article/details/50738043">使用 Spring Security 設定權限</a>
	 * </pre>
	 * 
	 * @return <code>{@link com.exfantasy.template.vo.response.ResponseVo}</code> 統一回應格式
	 */
	@PreAuthorize("hasAuthority('" + Role.ADMIN + "')") 
	@RequestMapping(value = "/testAuthorities", method = RequestMethod.GET)
	@ApiOperation(value = "測試權限用")
	public @ResponseBody ResponseVo testAuthorities() {
		return new ResponseVo(ResultCode.SUCCESS, "Hello admin");
	}
	
	/**
	 * <pre>
	 * 發送測試信件
	 * </pre>
	 * 
	 * @return String 若成功回傳 "Send mail succeed", 若失敗回傳 "Send mail failed, err-msg: ..."
	 */
	@RequestMapping(value = "/testSendMail", method = RequestMethod.GET)
	@ApiOperation(value = "測試發送信件")
	public @ResponseBody String testSendMail() {
		String mailTo = "tommy.yeh1112@gmail.com";
		String subject = "This is a test from SpringBoot";
		String text = "Hello~~";

		try {
			mailService.sendMail(mailTo, subject, text);
			return "Send mail succeed";
		} catch (MessagingException e) {
			return "Send mail failed, err-msg: " + e.getMessage();
		}
	}
	
	/**
	 * <pre>
	 * 測試 GET by RequestParam
	 * </pre>
	 * 
	 * @param data 測試資料
	 * @return String 回傳 "Response your data: " + data
	 */
	@RequestMapping(value = "/testGetByRequestParam", method = RequestMethod.GET)
	@ApiOperation(value = "測試 GET by RequestParam")
	public @ResponseBody String testGetByRequestParam(@RequestParam(value = "data", required = true) String data) {
		return "Response your data: " + data;
	}
	
	/**
	 * <pre>
	 * 測試 GET by PathVariable
	 * </pre>
	 * 
	 * @param data 測試資料
	 * @return String 回傳 "Response your data: " + data
	 */
	@RequestMapping(value = "/testGetByPathVariable/{data}", method = RequestMethod.GET)
	@ApiOperation(value = "測試 GET by PathVariable")
	public @ResponseBody String testGetByPathVariable(@PathVariable String data) {
		return "Response your data: " + data;
	}
}
