package com.exfantasy.template.controller;

import java.util.List;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.exfantasy.template.cnst.ResultCode;
import com.exfantasy.template.cnst.Role;
import com.exfantasy.template.services.amazon.AmazonS3Service;
import com.exfantasy.template.services.dropbox.DropboxService;
import com.exfantasy.template.services.mail.MailService;
import com.exfantasy.template.vo.response.RespCommon;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <pre>
 * 測試相關 API
 * </pre>
 * 
 * @author tommy.feng
 *
 */
@Controller
@RequestMapping(value = "/test")
@Api(value = "TestController - 測試相關 API")
public class TestController {
	
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private AmazonS3Service amazonS3Service;
	
	@Autowired
	private DropboxService dropboxService;
	
	/**
	 * <pre>
	 * 測試權限用
	 * 
	 * 參考:
	 * 	<a href="http://blog.csdn.net/tzdwsy/article/details/50738043">使用 Spring Security 設定權限</a>
	 * </pre>
	 * 
	 * @return <code>{@link com.exfantasy.template.vo.response.RespCommon}</code> 統一回應格式
	 */
	@PreAuthorize("hasAuthority('" + Role.ADMIN + "')") 
	@RequestMapping(value = "/testAuthorities", method = RequestMethod.GET)
	@ApiOperation(value = "測試權限用")
	public @ResponseBody RespCommon testAuthorities() {
		return new RespCommon(ResultCode.SUCCESS, "Hello admin");
	}
	
	/**
	 * <pre>
	 * 發送測試信件
	 * </pre>
	 * 
	 * @return String 若成功回傳 "Send mail to: <" + mailTo + "> with subject: <" + subject + "> and content: <" + mailContent + "> succeed", 若失敗回傳 "Send mail failed, err-msg: ..."
	 */
	@RequestMapping(value = "/testSendMail", method = RequestMethod.GET)
	@ApiOperation(value = "測試發送信件")
	public @ResponseBody String testSendMail(
		@RequestParam(value = "mailTo", required = true) String mailTo, 
		@RequestParam(value = "subject", required = true) String subject, 
		@RequestParam(value = "mailContent", required = true) String mailContent) {
		
		try {
			mailService.sendMail(mailTo, subject, mailContent);
			return "Send mail to: <" + mailTo + "> with subject: <" + subject + "> and content: <" + mailContent + "> succeed";
		} catch (MessagingException e) {
			return "Send mail failed, err-msg: " + e.getMessage();
		}
	}
	
	/**
	 * <pre>
	 * 測試 GET by RequestParam
	 * </pre>
	 * 
	 * @param data 測試資料
	 * @return String 回傳 "Response your data: " + data
	 */
	@RequestMapping(value = "/testGetByRequestParam", method = RequestMethod.GET)
	@ApiOperation(value = "測試 GET by RequestParam")
	public @ResponseBody String testGetByRequestParam(@RequestParam(value = "data", required = true) String data) {
		return "Response your data: " + data;
	}
	
	/**
	 * <pre>
	 * 測試 GET by PathVariable
	 * </pre>
	 * 
	 * @param data 測試資料
	 * @return String 回傳 "Response your data: " + data
	 */
	@RequestMapping(value = "/testGetByPathVariable/{data}", method = RequestMethod.GET)
	@ApiOperation(value = "測試 GET by PathVariable")
	public @ResponseBody String testGetByPathVariable(@PathVariable String data) {
		return "Response your data: " + data;
	}
	
	@RequestMapping(value = "/testAmazonS3GetFile", method = RequestMethod.GET)
	@ApiOperation(value = "測試從 Amazon S3 取回檔案", response = byte[].class)
	public ResponseEntity<byte[]> testAmazonS3GetFile(@RequestParam(value = "folderAndName", required = true) String folderAndName) {
		ResponseEntity<byte[]> downloadedFile;
		try {
			downloadedFile = amazonS3Service.download(folderAndName);
		} catch (Exception e) {
			logger.error("Try to download file from Amazon S3 with key: <{}> failed", folderAndName, e);
			return null;
		}
		return downloadedFile;
	}
	
	@RequestMapping(value = "/testAmazonS3ListBucketFiles", method = RequestMethod.GET)
	@ApiOperation(value = "測試從 Amazon S3 list 出 bucekt 下所有檔案資訊")
	public @ResponseBody List<S3ObjectSummary> testAmazonS3ListBucketFiles() {
		List<S3ObjectSummary> list = amazonS3Service.list();
		return list;
	}
	
	@RequestMapping(value = "/testDropboxGetAccountInformation", method = RequestMethod.GET)
	@ApiOperation(value = "測試取出 Dropbox Account 資訊")
	public @ResponseBody String testDropboxGetAccountInformation() {
		return dropboxService.getAccountInformation();
	}
}
