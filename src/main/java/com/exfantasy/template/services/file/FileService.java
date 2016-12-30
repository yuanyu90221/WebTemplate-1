package com.exfantasy.template.services.file;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.exfantasy.template.cnst.CloudStorage;
import com.exfantasy.template.cnst.ResultCode;
import com.exfantasy.template.exception.OperationException;
import com.exfantasy.template.mybatis.model.User;
import com.exfantasy.template.services.amazon.AmazonS3Service;
import com.exfantasy.template.services.dropbox.DropboxService;
import com.exfantasy.template.services.user.UserService;

@Service
public class FileService {
	private static final Logger logger = LoggerFactory.getLogger(FileService.class);
	
	private final String PROFILE_IMAGE_NAME = "profileImage.jpg";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AmazonS3Service amazonS3Service;
    
    @Autowired
    private DropboxService dropboxService;
    
    /**
     * <pre>
     * 上傳檔案到 Amazon S3 或 Dropbox
     * </pre>
     * 
     * @param multipartFile 欲上傳的檔案
     * @param pathAndName 欲儲存的路徑
     * 
     * @return {@link CloudStorage} 最後上傳的雲端空間
     */
    public CloudStorage uploadFile(MultipartFile multipartFile, String pathAndName) {
    	CloudStorage cloudStorage = null;
    	
    	String originalFileName = multipartFile.getOriginalFilename();
    	
    	boolean uploadToAmazonS3Succeed;
    	if (amazonS3Service.isEnable()) {
    		try {
    			logger.info(">>>>> Trying to upload file to Amazon S3, original file name: <{}>, Amazon S3 path and name: <{}>", originalFileName, pathAndName);
    			
    			uploadFileToAmazonS3(multipartFile, pathAndName);
    			
    			logger.info("<<<<< Upload file to Amazon S3 succeed, original file name: <{}>, Amazon S3 path and name: <{}>", originalFileName, pathAndName);
    			
    			cloudStorage = CloudStorage.AMAZON_S3;
    			uploadToAmazonS3Succeed = true;
    		}
    		catch (Exception e) {
    			amazonS3Service.setDisable();
    			amazonS3Service.setErrorMsg(e.getMessage());
    			logger.warn("~~~~~ Upload file to Amazon S3 failed, original file name: <{}>, Amazon S3 path and name: <{}>, error-msg: <{}>", originalFileName, pathAndName, e.getMessage());
    			uploadToAmazonS3Succeed = false;
    		} 
    	}
    	else {
    		logger.warn("~~~~~ Amazon S3 service is not available, error-msg: <{}>", amazonS3Service.getErrorMsg());
    		uploadToAmazonS3Succeed = false;
    	}
    	
    	if (!uploadToAmazonS3Succeed) {
			try {
				logger.info(">>>>> Trying to upload file to Dropbox, original file name: <{}>, Dropbox path and name: <{}>", originalFileName, pathAndName);
				
				uploadFileToDropbox(multipartFile, pathAndName);
				
				logger.info("<<<<< Upload file to Dropbox succeed, original file name: <{}>, Dropbox path and name: <{}>", originalFileName, pathAndName);
				
				cloudStorage = CloudStorage.DROPBOX;
				
			} catch (Exception e) {
				logger.error("~~~~~ Upload file to Dropbox failed, original file name: <{}>, Dropbox path and name: <{}>, error-msg: <{}>", e.getMessage());
				throw new OperationException(ResultCode.UPLOAD_FILE_FAILED);
			}
		}
    	return cloudStorage;
    }
    
    /**
     * <pre>
     * 上傳大頭照到 Amazon S3 或 Dropbox
     * </pre>
     * 
     * @param multipartFile 欲上傳的檔案
     * 
     * @return {@link CloudStorage} 最後儲存的雲端空間
     */
    public CloudStorage uploadProfileImage(MultipartFile multipartFile) {
    	String pathAndName = getProfileImagePathAndName();
		CloudStorage cloudStorage = uploadFile(multipartFile, pathAndName);
		return cloudStorage;
	}
    
    /**
	 * <pre>
	 * 從 Amazon S3 或 Dropbox 刪除大頭照
	 * </pre>
	 * 
	 * @param pathAndName 欲刪除的路徑
	 * 
	 * @return {@link CloudStorage} 最後刪除的雲端空間
	 */
	public CloudStorage deleteFile(String pathAndName) {
		CloudStorage cloudStorage = null;
		
		boolean deleteFromAmazonS3Succeed;
		if (amazonS3Service.isEnable()) {
			try {
				logger.info(">>>>> Trying to delete file from Amazon S3, Amazon S3 path and name: <{}>", pathAndName);
				
				deleteFileFromAmazonS3(pathAndName);
				
				logger.info("<<<<< Delete file from Amazon S3, Amazon S3 path and name: <{}>", pathAndName);
				
				cloudStorage = CloudStorage.AMAZON_S3;
				deleteFromAmazonS3Succeed = true;
			}
			catch (Exception e) {
				amazonS3Service.setDisable();
				amazonS3Service.setErrorMsg(e.getMessage());
				logger.error("~~~~~ Delete file from Amazon S3 failed, Amazon S3 path and name: <{}>, try to delete from Dropbox, error-msg: <{}>", e.getMessage());
				deleteFromAmazonS3Succeed = false;
			}
		}
		else {
			logger.warn("~~~~~ Amazon S3 service is not available, try to delete from Dropbox, error-msg: <{}>", amazonS3Service.getErrorMsg());
			deleteFromAmazonS3Succeed = false;
		}
		
		if (!deleteFromAmazonS3Succeed) {
			try {
				logger.info(">>>>> Trying to delete file from Dropbox, Dropbox path and name: <{}>", pathAndName);
				
				deleteFileFromDropbox(pathAndName);
				
				logger.info("<<<<< Delete file from Dropbox succeed, Dropbox path and name: <{}>", pathAndName);
				
				cloudStorage = CloudStorage.DROPBOX;

			} catch (Exception e) {
				logger.error("~~~~~ Delete file from dropbox failed, Dropbox path and name: <{}>, error-msg: <{}>", e.getMessage());
				throw new OperationException(ResultCode.DELETE_FILE_FAILED);
			}
		}
		return cloudStorage;
	}

	/**
     * <pre>
     * 從 Amazon S3 或 Dropbox 刪除大頭照
     * </pre>
     * 
	 * @return {@link CloudStorage} 最後刪除的雲端空間 
     */
    public CloudStorage deleteProfileImage() {
    	String pathAndName = getProfileImagePathAndName();
    	CloudStorage cloudStorage = deleteFile(pathAndName);
    	return cloudStorage;
    }
    
    /**
	 * <pre>
	 * 上傳檔案到 Amazon S3
	 * 
	 * 路徑注意:
	 * Amazon S3 -> 開頭不要 "/"
	 * 			
	 * </pre>
	 * 
	 * @param multipartFile 欲上傳的檔案
	 * @param pathAndName 欲放在 Amazon S3 的路徑
	 * 
	 * @throws IOException 
	 */
	private void uploadFileToAmazonS3(MultipartFile multipartFile, String pathAndName) throws IOException {
		long startTime = System.currentTimeMillis();
		logger.info("-----> Prepare to upload file to Amazon S3, Amazon S3 path and name: <{}>", pathAndName);

		amazonS3Service.upload(multipartFile, pathAndName);
		
		long timeSpent = System.currentTimeMillis() - startTime;
		logger.info("<----- Upload file to Amazon S3 done, time-spent: <{} ms>", timeSpent);
	}
	
	/**
	 * <pre>
	 * 上傳檔案到 Dropbox
	 * 
	 * 路徑注意:
	 * Dropbox -> 開頭要 "/"
	 * 
	 * </pre>
	 * 
	 * @param multipartFile 欲上傳的檔案
	 * @param pathAndName 欲放在 Dropbox 的路徑
	 * 
	 * @throws Exception
	 */
	private void uploadFileToDropbox(MultipartFile multipartFile, String pathAndName) throws Exception {
		long startTime = System.currentTimeMillis();
		logger.info("----> Prepare to upload file to Dropbox, Dropbox path and name: <{}>", pathAndName);
		
		dropboxService.upload(multipartFile, pathAndName);
		
		long timeSpent = System.currentTimeMillis() - startTime;
		logger.info("<---- Upload file to Dropbox done, time-spent: <{} ms>", timeSpent);
	}
	
	/**
	 * <pre>
	 * 從 Amazon S3 刪除檔案
	 * </pre>
	 * 
	 * @param pathAndName 欲刪除 Amazon S3 的檔案路徑
	 * 
	 * @throws Exception 
	 */
	private void deleteFileFromAmazonS3(String pathAndName) throws Exception {
		long startTime = System.currentTimeMillis();
		logger.info("----> Prepare to delete file from Amazon S3, Amazon S3 path and name: <{}>", pathAndName);
		
		amazonS3Service.deleteFile(pathAndName);
		
		long timeSpent = System.currentTimeMillis() - startTime;
		logger.info("<---- Delete file from Amazon S3 done, time-spent: <{} ms>", timeSpent);
	}

	/**
	 * <pre>
	 * 從 Dropbox 刪除檔案
	 * </pre>
	 * 
	 * @param pathAndName 欲刪除 Dropbox 的檔案路徑
	 * 
	 * @throws Exception 
	 */
	private void deleteFileFromDropbox(String pathAndName) throws Exception {
		long startTime = System.currentTimeMillis();
		logger.info("----> Prepare to delete file from Dropbox, Dropbox path and name: <{}>", pathAndName);
		
		dropboxService.delete(pathAndName);
		
		long timeSpent = System.currentTimeMillis() - startTime;
		logger.info("<---- Delete file from Dropbox done, time-spent: <{} ms>", timeSpent);
	}

	/**
	 * <pre>
	 * 取得要儲存大頭照的檔案位置
	 * </pre>
	 * 
	 * @return
	 */
	private String getProfileImagePathAndName() {
		User user = userService.getLoginUser();
		return user.getEmail() + "/" + PROFILE_IMAGE_NAME;
	}
}
