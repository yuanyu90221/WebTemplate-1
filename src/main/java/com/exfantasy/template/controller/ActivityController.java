package com.exfantasy.template.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.exfantasy.template.cnst.ResultCode;
import com.exfantasy.template.exception.OperationException;
import com.exfantasy.template.mybatis.model.User;
import com.exfantasy.template.services.activity.ActivityService;
import com.exfantasy.template.services.user.UserService;
import com.exfantasy.template.util.ErrorMsgUtil;
import com.exfantasy.template.vo.request.ActivityVo;
import com.exfantasy.template.vo.response.RespCommon;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value = "/activity")
@Api("ActivityController - 活動相關 API")
public class ActivityController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ActivityService activityService;
	
	/**
	 * <pre>
	 * 建立活動
	 * </pre>
	 * 
	 * @param activityVo 前端發過來建立活動所需資料, 參考物建: <code>{@link com.exfantasy.template.vo.request.ActivityVo}</code>
	 * @param result 綁定物件結果, 參考物件: <code>{@link org.springframework.validation.BindingResult}</code>
	 * @return <code>{@link com.exfantasy.template.vo.response.RespCommon}</code> 回應操作結果
	 */
	@RequestMapping(value = "/create_activity", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "建立活動", notes = "建立活動", response = RespCommon.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "activityVo", value = "建立活動需填入資料", required = true, dataType = "ActivityVo")
	})
	public @ResponseBody RespCommon createActivity(@Validated @RequestBody final ActivityVo activityVo, BindingResult result) {
		if (result.hasErrors()) {
			String errorMsg = ErrorMsgUtil.getErrorMsgs(result);
			throw new OperationException(ResultCode.INVALID_FORMAT, errorMsg);
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User user = userService.queryUserByEmail(email);
		
		activityService.createActivity(user, activityVo);
		return new RespCommon(ResultCode.SUCCESS, "Create activity succeed");
	}
}
