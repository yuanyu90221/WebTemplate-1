package com.exfantasy.template.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.exfantasy.template.constant.ResultCode;
import com.exfantasy.template.exception.OperationException;
import com.exfantasy.template.mybatis.model.User;
import com.exfantasy.template.services.user.UserService;
import com.exfantasy.template.util.ErrorMsgUtil;
import com.exfantasy.template.vo.request.RegisterVo;
import com.exfantasy.template.vo.response.ResponseVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value = "/user")
@Api("UserController - 使用者相關api")
public class UserController {
	
//	@Autowired(required = true)
//	private HttpServletRequest request;

	@Autowired
	private UserService userService;
	
//	@RequestMapping(value = "/register", method = RequestMethod.GET)
//	public String returnUserPage(Model model) {
//		model.addAttribute("registerVo", new RegisterVo());
//		return "register";
//	}
	
	@RequestMapping(value = "/do_register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "用戶註冊", notes = "給用戶輸入資料新增帳號用", response = ResponseVo.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "registerVo", value = "用戶註冊需填入的資料", required = true, dataType = "RegisterVo")
	})
	public @ResponseBody ResponseVo register(@Validated @RequestBody final RegisterVo registerVo, BindingResult result) {
		if (result.hasErrors()) {
			String errorMsg = ErrorMsgUtil.getErrorMsgs(result);
			throw new OperationException(ResultCode.INVALID_FORMAT, errorMsg);
		}
		userService.register(registerVo);
		return new ResponseVo(ResultCode.SUCCESS, "Register succeed");
	}
	
	@RequestMapping(value = "/get_by_email", method = RequestMethod.GET)
	@ApiOperation(value = "使用 email 查詢用戶")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "email", value = "註冊當時所使用的 email", required = true, dataType = "String")
	})
	public @ResponseBody User queryUserByEmail(@RequestParam(value = "email", required = true) String email) {
		User user = userService.queryUserByEmail(email);
		return user;
	}
}
