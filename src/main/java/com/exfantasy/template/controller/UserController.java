package com.exfantasy.template.controller;

import java.util.List;

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
import org.springframework.web.servlet.ModelAndView;

import com.exfantasy.template.cnst.ResultCode;
import com.exfantasy.template.exception.OperationException;
import com.exfantasy.template.mybatis.model.User;
import com.exfantasy.template.mybatis.model.UserRole;
import com.exfantasy.template.services.user.UserService;
import com.exfantasy.template.util.ErrorMsgUtil;
import com.exfantasy.template.vo.request.RegisterVo;
import com.exfantasy.template.vo.response.ResponseVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <pre>
 * 使用者相關 API
 * </pre>
 * 
 * @author tommy.feng
 *
 */
@Controller
@RequestMapping(value = "/user")
@Api("UserController - 使用者相關 API")
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
	
	/**
	 * <pre>
	 * 用戶註冊 
	 * </pre>
	 * 
	 * @param registerVo 前端發過來的資料, 參考物件: <code>{@link com.exfantasy.template.vo.request.RegisterVo}</code>
	 * @param result 綁定物件結果, 參考物件: <code>{@link org.springframework.validation.BindingResult}</code>
	 * @return <code>{@link com.exfantasy.template.vo.response.ResponseVo}</code> 回應操作結果
	 */
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
	
	/**
	 * <pre>
	 * 使用 email 查詢用戶
	 * </pre>
	 * 
	 * @param email 用戶當初註冊的 email
	 * @return <code>{@link com.exfantasy.template.mybatis.model.User}</code> 用戶資訊 
	 */
	@RequestMapping(value = "/get_by_email", method = RequestMethod.GET)
	@ApiOperation(value = "使用 email 查詢用戶")
	public @ResponseBody User queryUserByEmail(@RequestParam(value = "email", required = true) String email) {
		return userService.queryUserByEmail(email);
	}
	
	/**
	 * <pre>
	 * 使用 email 查詢用戶所擁有角色
	 * </pre>
	 * 
	 * @param email 用戶當初註冊的 email
	 * @return
	 */
	@RequestMapping(value = "/get_user_roles_by_email", method = RequestMethod.GET)
	@ApiOperation(value = "使用 email 查詢用戶所擁有角色")
	public @ResponseBody List<UserRole> queryUserRolesByEmail(@RequestParam(value = "email", required = true) String email) {
		return userService.queryUserRolesByEmail(email);
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}
}
