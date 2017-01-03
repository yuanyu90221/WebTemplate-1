package com.exfantasy.template.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.exfantasy.template.cnst.CloudStorage;
import com.exfantasy.template.cnst.ResultCode;
import com.exfantasy.template.mybatis.model.User;
import com.exfantasy.template.services.file.FileService;
import com.exfantasy.template.services.session.SessionService;
import com.exfantasy.template.vo.response.RespCommon;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <pre>
 * 檔案管理相關 API
 * </pre>
 * 
 * @author tommy.feng
 *
 */
@Controller
@RequestMapping(value = "file")
@Api("FileController - 檔案管理相關 API")
public class FileController {
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private SessionService sessionService;
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ApiOperation(value = "上傳檔案", notes = "上傳檔案", response = RespCommon.class)
	public @ResponseBody RespCommon updateProfileImage(@RequestParam(value = "file", required = true) MultipartFile multipartFile) {
		if (!multipartFile.isEmpty()) {
			User user = sessionService.getLoginUser();
			String pathAndName = user.getEmail() + "/" + multipartFile.getOriginalFilename();
			CloudStorage cloudStorage = fileService.uploadFile(multipartFile, pathAndName);
			return new RespCommon(ResultCode.SUCCESS, "Upload file to " + cloudStorage + " succeed");
		}
		else {
			return new RespCommon(ResultCode.FILE_IS_EMPTY);
		}
	}
}
