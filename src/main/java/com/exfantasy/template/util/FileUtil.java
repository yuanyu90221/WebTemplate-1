package com.exfantasy.template.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 * 根據副檔名組成對應的 HttpHeaders
 * 
 * @author tommy.feng
 *
 */
public class FileUtil {

	public static HttpHeaders getHttpHeaderByFileName(String pathAndName, byte[] bytes) {
		String fileName = pathAndName.substring(pathAndName.lastIndexOf("/") + 1, pathAndName.length());
		String extFileName = pathAndName.substring(pathAndName.lastIndexOf(".") + 1, pathAndName.length());
		
		// http://stackoverflow.com/questions/5690228/spring-mvc-how-to-return-image-in-responsebody
		HttpHeaders httpHeaders = new HttpHeaders();

		if (extFileName.compareToIgnoreCase("jpg") == 0 || extFileName.compareToIgnoreCase("jpeg") == 0) {
			httpHeaders.setContentType(MediaType.IMAGE_JPEG);
	        httpHeaders.setContentLength(bytes.length);
		}
		else if (extFileName.compareToIgnoreCase("pdf") == 0) {
			httpHeaders.setContentType(MediaType.parseMediaType("application/pdf"));
			httpHeaders.setContentDispositionFormData(fileName, fileName);
			httpHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		}

		return httpHeaders;
	}
}
