package com.exfantasy.template.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.exfantasy.template.constant.ResultCode;
import com.exfantasy.template.exception.OperationException;
import com.exfantasy.template.services.consume.ConsumeService;
import com.exfantasy.template.util.ErrorMsgUtil;
import com.exfantasy.template.vo.request.ConsumeVo;
import com.exfantasy.template.vo.response.ResponseVo;

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
	private ConsumeService consumeService;
	
	/**
	 * <pre>
	 * 新增記帳資料
	 * </pre>
	 */
	@RequestMapping(value = "/add_consume", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "新增記帳資料", notes = "新增記帳資料", response = ResponseVo.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "consumeVo", value = "新增記帳資料需填入", required = true, dataType = "ConsumeVo")
	})
	public @ResponseBody ResponseVo addConsume(@Validated @RequestBody final ConsumeVo consumeVo, BindingResult result) {
		if (result.hasErrors()) {
			String errorMsg = ErrorMsgUtil.getErrorMsgs(result);
			throw new OperationException(ResultCode.INVALID_FORMAT, errorMsg);
		}
		consumeService.addConsume(consumeVo);
		return new ResponseVo(ResultCode.SUCCESS, "Add consume data succeed");
	}
}
