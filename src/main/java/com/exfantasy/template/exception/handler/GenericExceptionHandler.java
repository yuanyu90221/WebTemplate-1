package com.exfantasy.template.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.exfantasy.template.cnst.ResultCode;
import com.exfantasy.template.exception.OperationException;
import com.exfantasy.template.vo.response.RespCommon;

/**
 * <pre>
 * 統一處理系統例外用
 * </pre>
 * 
 * @author tommy.feng
 *
 */
@ControllerAdvice
public class GenericExceptionHandler {
	
	private Logger logger = LoggerFactory.getLogger(GenericExceptionHandler.class);
	
	@ExceptionHandler(OperationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public RespCommon handleOperationException(OperationException ex) {
		logger.error("OperationException raised, error-code: <{}>, error-msg: <{}>", ex.getErrorCode(), ex.getErrorMessage());
		return new RespCommon(ex);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public RespCommon handleAccessDeniedException(AccessDeniedException ex) {
		logger.error("AccessDeniedException raised", ex);
		return new RespCommon(ResultCode.ACCESS_DENIED);
	}
	
	@ExceptionHandler(DuplicateKeyException.class)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public RespCommon handleDuplicateKeyException(DuplicateKeyException ex) {
		logger.warn("DuplicateKeyException raised", ex);
		return new RespCommon(ResultCode.DUPLICATE_KEY, ex.getMessage());
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public RespCommon handleAllException(Exception ex) {
		logger.error("Exception raised", ex);
		return new RespCommon(ResultCode.SYSTEM_EXCEPTION, ex.getMessage());
	}
}
