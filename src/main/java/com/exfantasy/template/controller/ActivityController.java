package com.exfantasy.template.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.exfantasy.template.cnst.ResultCode;
import com.exfantasy.template.exception.OperationException;
import com.exfantasy.template.mybatis.model.Activity;
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
import io.swagger.annotations.ApiParam;

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
	 * @param activityVo 前端發過來建立活動所需資料, 參考物件: <code>{@link com.exfantasy.template.vo.request.ActivityVo}</code>
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
		
		User user = userService.getLoginUser();
		
		activityService.createActivity(user, activityVo);
		return new RespCommon(ResultCode.SUCCESS, "Create activity succeed");
	}
	
	/**
	 * <pre>
	 * TODO 參加活動
	 * </pre>
	 * 
	 * @param activityId 欲參加的活動 ID
	 * @return <code>{@link com.exfantasy.template.vo.response.RespCommon}</code> 回應操作結果
	 */
	@RequestMapping(value = "/joinActivity/{activityId}", method = RequestMethod.PUT)
	@ApiOperation(value = "參加活動", notes = "參加活動", response = RespCommon.class)
	public @ResponseBody RespCommon joinActivity(@ApiParam("欲參加的活動 ID") @PathVariable("activityId") Long activityId) {
		System.out.println("In joinActivity with activityId: " + activityId);
		return new RespCommon(ResultCode.SUCCESS, "Join activity succeed");
	}
	
	/**
	 * <pre>
	 * 查詢登入者所建立的活動
	 * </pre>
	 * 
	 * @return <code>{@link com.exfantasy.template.mybatis.model.Activity}</code> 活動資訊
	 */
	@RequestMapping(value = "/get_created_activities", method = RequestMethod.GET)
	@ApiOperation(value = "查詢使用者所建立的活動", notes = "查詢使用者所建立的活動", response = Activity.class)
	public @ResponseBody List<Activity> getCreatedActivities() {
		User user = userService.getLoginUser();
		
		List<Activity> activities = activityService.getCreatedActivities(user);
		
		return activities;
	}
	
	/**
	 * <pre>
	 * 查詢使用者所有參與的活動
	 * </pre>
	 * 
	 * @return <code>{@link com.exfantasy.template.mybatis.model.Activity}</code> 活動資訊
	 */
	@RequestMapping(value = "/get_joined_activities", method = RequestMethod.GET)
	@ApiOperation(value = "查詢使用者參與的所有的活動", notes = "查詢使用者參與的所有的活動", response = Activity.class)
	public @ResponseBody List<Activity> getJoinedActivities() {
		User user = userService.getLoginUser();
		
		List<Activity> activities = activityService.getJoinedActivities(user);
		
		return activities;
	}
}
