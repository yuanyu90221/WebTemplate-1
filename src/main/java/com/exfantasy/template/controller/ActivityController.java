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
import org.springframework.web.bind.annotation.ResponseBody;

import com.exfantasy.template.cnst.ResultCode;
import com.exfantasy.template.exception.OperationException;
import com.exfantasy.template.mybatis.model.Activity;
import com.exfantasy.template.mybatis.model.User;
import com.exfantasy.template.services.activity.ActivityService;
import com.exfantasy.template.services.session.SessionService;
import com.exfantasy.template.util.ErrorMsgUtil;
import com.exfantasy.template.vo.request.ActivityMessageVo;
import com.exfantasy.template.vo.request.ActivityVo;
import com.exfantasy.template.vo.response.ActivityMessagesResp;
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
	private SessionService sessionService;
	
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
		
		User user = sessionService.getLoginUser();
		
		activityService.createActivity(user, activityVo);
		return new RespCommon(ResultCode.SUCCESS, "Create activity succeed");
	}
	
	/**
	 * <pre>
	 * 參加活動
	 * </pre>
	 * 
	 * @param activityId 欲參加的活動 ID
	 * @return <code>{@link com.exfantasy.template.vo.response.RespCommon}</code> 回應操作結果
	 */
	@RequestMapping(value = "/join_activity/{activityId}", method = RequestMethod.PUT)
	@ApiOperation(value = "參加活動", notes = "參加活動", response = RespCommon.class)
	public @ResponseBody RespCommon joinActivity(@ApiParam("欲參加的活動 ID") @PathVariable("activityId") Integer activityId) {
		User user = sessionService.getLoginUser();
		
		activityService.joinActivity(user.getUserId(), activityId);
		
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
		User user = sessionService.getLoginUser();
		
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
		User user = sessionService.getLoginUser();
		
		List<Activity> activities = activityService.getJoinedActivities(user);
		
		return activities;
	}
	
	/**
	 * <pre>
	 * 查詢活動的所有參與者
	 * </pre>
	 * 
	 * @param activityId 欲查詢的活動 ID
	 * @return <code>{@link com.exfantasy.template.mybatis.model.User}</code> 使用者資訊
	 */
	@RequestMapping(value = "/get_joined_users/{activityId}", method = RequestMethod.GET)
	@ApiOperation(value = "查詢活動的所有參與者", notes = "查詢活動的所有參與者", response = User.class)
	public @ResponseBody List<User> getJoinedUsers(@ApiParam("欲查詢的活動 ID") @PathVariable("activityId") Integer activityId) {
		List<User> joinedUsers = activityService.getJoinedUsers(activityId);
		
		return joinedUsers;
	}
	
	/**
	 * <pre>
	 * 新增活動留言
	 * </pre>
	 * 
	 * @param activityMsgVo 前端發過來對活動留言所需資料, 參考物件: <code>{@link com.exfantasy.template.vo.request.ActivityMessageVo}</code>
	 * @param result 綁定物件結果, 參考物件: <code>{@link org.springframework.validation.BindingResult}</code>
	 * @return <code>{@link com.exfantasy.template.vo.response.RespCommon}</code> 回應操作結果
	 */
	@RequestMapping(value = "/leave_message", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "對活動留言", notes = "對活動留言", response = RespCommon.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "activityMsgVo", value = "對活動留言需填入資料", required = true, dataType = "ActivityMessageVo")
	})
	public @ResponseBody RespCommon leaveMessage(@Validated @RequestBody final ActivityMessageVo activityMsgVo, BindingResult result) {
		if (result.hasErrors()) {
			String errorMsg = ErrorMsgUtil.getErrorMsgs(result);
			throw new OperationException(ResultCode.INVALID_FORMAT, errorMsg);
		}
		
		User user = sessionService.getLoginUser();
		
		activityService.leaveMessage(user.getUserId(), activityMsgVo.getActivityId(), activityMsgVo.getMessage());
		
		return new RespCommon(ResultCode.SUCCESS, "Leave message succeed");
	}
	
	/**
	 * <pre>
	 * 查詢某一個活動的所有留言
	 * </pre>
	 * 
	 * @param activityId 欲查詢的活動 ID
	 * @return <code>{@link com.exfantasy.template.mybatis.model.ActivityMessages}</code> 活動的所有留言
	 */
	@RequestMapping(value = "/get_activity_messages/{activityId}", method = RequestMethod.GET)
	@ApiOperation(value = "查詢活動的所有留言", notes = "查詢活動的所有留言", response = ActivityMessagesResp.class)
	public @ResponseBody List<ActivityMessagesResp> getActivityMessages(@ApiParam("欲查詢的活動 ID") @PathVariable("activityId") Integer activityId) {
		List<ActivityMessagesResp> activityMessages = activityService.getActivityMessages(activityId);
		return activityMessages;
	}
}
