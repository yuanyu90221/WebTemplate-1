package com.exfantasy.template.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.exfantasy.template.cnst.CloudStorage;
import com.exfantasy.template.cnst.ResultCode;
import com.exfantasy.template.exception.OperationException;
import com.exfantasy.template.mybatis.model.User;
import com.exfantasy.template.mybatis.model.UserRole;
import com.exfantasy.template.services.session.SessionService;
import com.exfantasy.template.services.user.UserService;
import com.exfantasy.template.util.ErrorMsgUtil;
import com.exfantasy.template.vo.request.RegisterVo;
import com.exfantasy.template.vo.response.RespCommon;

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
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SessionService sessionService;
	
	/**
	 * <pre>
	 * 用戶註冊 
	 * </pre>
	 * 
	 * @param registerVo 前端發過來的資料, 參考物件: <code>{@link com.exfantasy.template.vo.request.RegisterVo}</code>
	 * @param result 綁定物件結果, 參考物件: <code>{@link org.springframework.validation.BindingResult}</code>
	 * @return <code>{@link com.exfantasy.template.vo.response.RespCommon}</code> 回應操作結果
	 */
	@RequestMapping(value = "/do_register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "用戶註冊", notes = "給用戶輸入資料新增帳號用", response = RespCommon.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "registerVo", value = "用戶註冊需填入的資料", required = true, dataType = "RegisterVo")
	})
	public @ResponseBody RespCommon register(@Validated @RequestBody final RegisterVo registerVo, BindingResult result) {
		if (result.hasErrors()) {
			String errorMsg = ErrorMsgUtil.getErrorMsgs(result);
			throw new OperationException(ResultCode.INVALID_FORMAT, errorMsg);
		}
		userService.register(registerVo);
		return new RespCommon(ResultCode.SUCCESS, "Register succeed");
	}
	
	/**
	 * <pre>
	 * 使用 email 查詢用戶
	 * </pre>
	 * 
	 * @param email 用戶當初註冊的 email
	 * @return <code>{@link com.exfantasy.template.mybatis.model.User}</code> 用戶資訊 
	 */
	@RequestMapping(value = "/get_user_by_email", method = RequestMethod.GET)
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
	
	/**
	 * <pre>
	 * 取得登入者的資訊
	 * </pre>
	 * 
	 * @return {@link com.exfantasy.template.mybatis.model.User}
	 */
	@RequestMapping(value = "/get_my_information", method = RequestMethod.GET)
	@ApiOperation(value = "取得我(登入者)的資訊")
	public @ResponseBody User getMyInformation() {
		return sessionService.getLoginUser();
	}
	
	/**
	 * <pre>
	 * 修改密碼
	 * </pre>
	 */
	@RequestMapping(value = "/change_password", method = RequestMethod.POST)
	@ApiOperation(value = "修改密碼")
	public @ResponseBody RespCommon changePassword(
		@RequestParam(value = "oldPassword", required = true) String oldPassword, 
		@RequestParam(value = "newPassword", required = true) String newPassword) {
		userService.changePassword(sessionService.getLoginUser(), oldPassword, newPassword);
		return new RespCommon(ResultCode.SUCCESS, "Change password succeed");
	}
 	
	/**
	 * <pre>
	 * 忘記密碼
	 * </pre>
	 */
	@RequestMapping(value = "/forgot_password", method = RequestMethod.GET)
	@ApiOperation(value = "忘記密碼")
	public @ResponseBody RespCommon forgotPassword(
		@RequestParam(value = "email", required = true) String email) {
		userService.forgotPassword(email);
		return new RespCommon(ResultCode.SUCCESS, "Send forget password mail succeed");
	}
	
	/**
	 * <pre>
	 * 上傳大頭照
	 * </pre>
	 * 
	 * @param multipartFile
	 * 
	 * @return <code>{@link com.exfantasy.template.vo.response.RespCommon}</code> 回應操作結果
	 */
	@RequestMapping(value = "/upload_profile_image", method = RequestMethod.POST)
	@ApiOperation(value = "上傳大頭貼")
	public @ResponseBody RespCommon updateProfileImage(@RequestParam(value = "file", required = true) MultipartFile multipartFile) {
		if (!multipartFile.isEmpty()) {
			CloudStorage cloudStorage = userService.uploadProfileImage(multipartFile);
			return new RespCommon(ResultCode.SUCCESS, "Upload profile image to " + cloudStorage + " succeed");
		}
		else {
			return new RespCommon(ResultCode.FILE_IS_EMPTY);
		}
	}
	
	/**
	 * <pre>
	 * 刪除大頭照
	 * </pre>
	 * 
	 * @return <code>{@link com.exfantasy.template.vo.response.RespCommon}</code> 回應操作結果
	 */
	@RequestMapping(value = "/delete_profile_image", method = RequestMethod.PUT)
	@ApiOperation(value = "刪除大頭貼")
	public @ResponseBody RespCommon deleteProfileImage() {
		CloudStorage cloudStorage = userService.deleteProfileImage();
		return new RespCommon(ResultCode.SUCCESS, "Delete profile image from " + cloudStorage + " succeed");
	}
	
	/**
	 * <pre>
	 * 取得大頭照
	 * </pre>
	 * 
	 * @return ResponseEntity<byte[]> 檔案內容
	 */
	@RequestMapping(value = "/get_profile_image", method = RequestMethod.GET)
	@ApiOperation(value = "取得大頭貼", response = byte[].class)
	public ResponseEntity<byte[]> getProfileImage() {
		ResponseEntity<byte[]> downloadedFile = null;
		downloadedFile = userService.getProfileImage();
		return downloadedFile;
	}
}
