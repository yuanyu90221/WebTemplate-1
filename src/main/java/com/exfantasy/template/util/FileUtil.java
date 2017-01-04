package com.exfantasy.template.util;

import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 * <pre>
 * 根據副檔名組成對應的 HttpHeaders
 * 
 * 參考: <a href="http://websystique.com/springmvc/spring-mvc-4-file-download-example/">spring-mvc-4-file-download-example</a>
 * </pre>
 * 
 * @author tommy.feng
 *
 */
public class FileUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

	public static HttpHeaders getHttpHeaderByFileName(String pathAndName, byte[] bytes) {
		String fileName = pathAndName.substring(pathAndName.lastIndexOf("/") + 1, pathAndName.length());
		
		HttpHeaders httpHeaders = new HttpHeaders();

		String sMediaType = URLConnection.guessContentTypeFromName(fileName);
		if (sMediaType == null) {
			logger.warn("MediaType is not detectable, will take default");
			sMediaType = "application/octet-stream";
		}
		MediaType mediaType = MediaType.parseMediaType(sMediaType);
		
		logger.info("The file MediaType: <{}>", mediaType);
		
		try {
			fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
		} catch (UnsupportedEncodingException e) {
			logger.warn("UnsupportedEncodingException raised while encoding fileName with UTF-8", e);
		}
		
		httpHeaders.setContentType(mediaType);
		httpHeaders.setContentDispositionFormData("file", fileName);
        httpHeaders.setContentLength(bytes.length);

		return httpHeaders;
	}
}
