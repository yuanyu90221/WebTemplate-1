package com.exfantasy.template.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class FileConvertUtil {

	/**
	 * <pre>
	 * 轉換 MultipartFile 為 File
	 * 
	 * <a href="http://stackoverflow.com/questions/24339990/how-to-convert-a-multipart-file-to-file">how-to-convert-a-multipart-file-to-file</a>
	 * </pre>
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static File convert(MultipartFile file) throws IOException {    
	    File convFile = new File(file.getOriginalFilename());
	    FileOutputStream fos = new FileOutputStream(convFile); 
	    fos.write(file.getBytes());
	    fos.close(); 
	    return convFile;
	}
}
