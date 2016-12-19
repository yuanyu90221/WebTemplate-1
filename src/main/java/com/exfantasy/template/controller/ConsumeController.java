package com.exfantasy.template.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.exfantasy.template.cnst.ResultCode;
import com.exfantasy.template.exception.OperationException;
import com.exfantasy.template.mybatis.model.Consume;
import com.exfantasy.template.mybatis.model.User;
import com.exfantasy.template.services.consume.ConsumeService;
import com.exfantasy.template.services.user.UserService;
import com.exfantasy.template.util.ErrorMsgUtil;
import com.exfantasy.template.vo.request.ConsumeVo;
import com.exfantasy.template.vo.response.RespCommon;
import com.exfantasy.template.vo.response.RespReward;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <pre>
 * 記帳相關 API
 * </pre>
 * 
 * @author tommy.feng
 *
 */
@Controller
@RequestMapping(value = "/consume")
@Api("ConsumeController - 記帳相關 API")
public class ConsumeController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ConsumeService consumeService;
	
	/**
	 * <pre>
	 * 新增記帳資料
	 * </pre>
	 * 
	 * @param consumeVo 前端發過來的記帳資料, 參考物件: <code>{@link com.exfantasy.template.vo.request.ConsumeVo}</code>
	 * @param result 綁定物件結果, 參考物件: <code>{@link org.springframework.validation.BindingResult}</code>
	 * @return <code>{@link com.exfantasy.template.vo.response.RespCommon}</code> 回應操作結果
	 */
	@RequestMapping(value = "/add_consume", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "新增記帳資料", notes = "新增記帳資料", response = RespCommon.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "consumeVo", value = "新增記帳資料需填入", required = true, dataType = "ConsumeVo")
	})
	public @ResponseBody RespCommon addConsume(@Validated @RequestBody final ConsumeVo consumeVo, BindingResult result) {
		if (result.hasErrors()) {
			String errorMsg = ErrorMsgUtil.getErrorMsgs(result);
			throw new OperationException(ResultCode.INVALID_FORMAT, errorMsg);
		}
		
		// https://www.mkyong.com/spring-security/get-current-logged-in-username-in-spring-security/
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User user = userService.queryUserByEmail(email);
		
		consumeService.addConsume(user, consumeVo);
		return new RespCommon(ResultCode.SUCCESS, "Add consume data succeed");
	}
	
	/**
	 * <pre>
	 * 更新記帳資料
	 * </pre>
	 * 
	 * @param consumeVo 前端發過來要更新的記帳資料, 參考物件: <code>{@link com.exfantasy.template.vo.request.ConsumeVo}</code>
	 * @param result 綁定物件結果, 參考物件: <code>{@link org.springframework.validation.BindingResult}</code>
	 * @return <code>{@link com.exfantasy.template.vo.response.RespCommon}</code> 回應操作結果
	 */
	@RequestMapping(value = "/upd_consume", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "更新記帳資料", notes = "更新記帳資料", response = RespCommon.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "consumeVo", value = "更新記帳資料需填入", required = true, dataType = "ConsumeVo")
	})
	public @ResponseBody RespCommon updConsume(@Validated @RequestBody final ConsumeVo consumeVo, BindingResult result) {
		if (result.hasErrors()) {
			String errorMsg = ErrorMsgUtil.getErrorMsgs(result);
			throw new OperationException(ResultCode.INVALID_FORMAT, errorMsg);
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User user = userService.queryUserByEmail(email);
		
		consumeService.updConsume(user, consumeVo);
		return new RespCommon(ResultCode.SUCCESS, "Update consume data succeed");
	}
	
	/**
	 * <pre>
	 * 刪除記帳資料
	 * </pre>
	 * 
	 * @param consumeVo 前端發過來要刪除的記帳資料, 參考物件: <code>{@link com.exfantasy.template.vo.request.ConsumeVo}</code>
	 * @param result 綁定物件結果, 參考物件: <code>{@link org.springframework.validation.BindingResult}</code>
	 * @return <code>{@link com.exfantasy.template.vo.response.RespCommon}</code> 回應操作結果
	 */
	@RequestMapping(value = "/del_consume", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "刪除記帳資料", notes = "刪除記帳資料", response = RespCommon.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "consumeVo", value = "刪除記帳資料需填入", required = true, dataType = "ConsumeVo")
	})
	public @ResponseBody RespCommon delConsume(@Validated @RequestBody final ConsumeVo consumeVo, BindingResult result) {
		if (result.hasErrors()) {
			String errorMsg = ErrorMsgUtil.getErrorMsgs(result);
			throw new OperationException(ResultCode.INVALID_FORMAT, errorMsg);
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User user = userService.queryUserByEmail(email);
		
		consumeService.delConsume(user, consumeVo);
		return new RespCommon(ResultCode.SUCCESS, "Update consume data succeed");
	}
	
	/**
	 * <pre>
	 * 查詢記帳資料
	 * </pre>
	 * 
	 * @param startDate 開始時間
	 * @param endDate 結束時間
	 * @param type 分類
	 * @param prodName 商品名稱
	 * @param lotteryNo 發票號碼
	 * @return List<<code>{@link com.exfantasy.template.mybatis.model.Consume}</code>> 記帳資訊
	 */
	@RequestMapping(value = "/get_consume", method = RequestMethod.GET)
	@ApiOperation(value = "查詢記帳資料", notes = "查詢記帳資料", response = Consume.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "startDate", value = "開始時間", required = false, dataType = "Date"),
		@ApiImplicitParam(name = "endDate", value = "結束時間", required = false, dataType = "Date"),
		@ApiImplicitParam(name = "type", value = "分類", required = false, dataType = "Integer"),
		@ApiImplicitParam(name = "prodName", value = "商品名稱", required = false, dataType = "String"),
		@ApiImplicitParam(name = "lotteryNo", value = "發票號碼", required = false, dataType = "String")
	})
	public @ResponseBody List<Consume> getConsume(
			@RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate, 
			@RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate,
			@RequestParam(value = "type", required = false) Integer type,
			@RequestParam(value = "prodName", required = false) String prodName,
			@RequestParam(value = "lotteryNo", required = false) String lotteryNo
		) {
		if (endDate != null) {
			setEndDateTime(endDate);
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User user = userService.queryUserByEmail(email);
		
		List<Consume> consumes = consumeService.getConsume(user, startDate, endDate, type, prodName, lotteryNo);
		return consumes;
	}
	
	@RequestMapping(value = "/get_latest_reward_numbers", method = RequestMethod.GET)
	@ApiOperation(value = "取得最新發票開獎號碼", notes = "取得最新發票開獎號碼", response = RespReward.class)
	public @ResponseBody List<RespReward> getLatestRewardNumbers() {
		List<RespReward> latestRewardNumbers = consumeService.getLatestRewardNumbers();
		return latestRewardNumbers;
	}
	
	private void setEndDateTime(Date endDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(endDate);
		cal.set(Calendar.HOUR, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		endDate = cal.getTime();
	}
}