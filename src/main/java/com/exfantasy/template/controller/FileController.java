package com.exfantasy.template.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.exfantasy.template.cnst.CloudStorage;
import com.exfantasy.template.cnst.ResultCode;
import com.exfantasy.template.exception.OperationException;
import com.exfantasy.template.mybatis.model.User;
import com.exfantasy.template.services.file.FileService;
import com.exfantasy.template.services.session.SessionService;
import com.exfantasy.template.vo.response.RespCommon;
import com.exfantasy.template.vo.response.ListFileResp;

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
	public @ResponseBody RespCommon upload(@RequestParam(value = "file", required = true) MultipartFile multipartFile) {
		if (!multipartFile.isEmpty()) {
			User user = sessionService.getLoginUser();
			String pathAndName = user.getEmail() + "/" + multipartFile.getOriginalFilename();
			CloudStorage cloudStorage = fileService.uploadFile(multipartFile, pathAndName);
			return new RespCommon(ResultCode.SUCCESS, "Upload file to " + cloudStorage + " succeed");
		}
		else {
			throw new OperationException(ResultCode.FILE_IS_EMPTY);
		}
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ApiOperation(value = "檔案列表", notes = "列出雲端空間的檔案", responseContainer = "List", response = ListFileResp.class)
	public @ResponseBody List<ListFileResp> listFiles() {
		User user = sessionService.getLoginUser();
		return fileService.listFiles(user.getEmail());
	}
	
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	@ApiOperation(value = "下載檔案", notes = "下載檔案")
	public ResponseEntity<byte[]> downloadFile(@RequestParam(value = "pathAndName", required = true) String pathAndName) {
		return fileService.downloadFile(pathAndName);
	}
}
