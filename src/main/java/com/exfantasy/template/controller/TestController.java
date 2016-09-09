package com.exfantasy.template.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.exfantasy.template.constant.ResultCode;
import com.exfantasy.template.constant.Role;
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
	@PreAuthorize("hasAuthority('admin')")
	// FIXME 使用變數無法正常運作, 再查看看為什麼
	// @PreAuthorize("hasAuthority(' " + Role.ADMIN + "')") 
	@RequestMapping(value = "/authorities", method = RequestMethod.GET)
	@ApiOperation(value = "測試權限用")
	public @ResponseBody ResponseVo test() {
		return new ResponseVo(ResultCode.SUCCESS, "Hello admin");
	}
}
