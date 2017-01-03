package com.exfantasy.template.services.file;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.dropbox.core.v2.files.FileMetadata;
import com.exfantasy.template.cnst.CloudStorage;
import com.exfantasy.template.cnst.ResultCode;
import com.exfantasy.template.exception.OperationException;
import com.exfantasy.template.mybatis.model.User;
import com.exfantasy.template.services.amazon.AmazonS3Service;
import com.exfantasy.template.services.dropbox.DropboxService;
import com.exfantasy.template.services.session.SessionService;
import com.exfantasy.template.vo.response.ListFileResp;

@Service
public class FileService {
	private static final Logger logger = LoggerFactory.getLogger(FileService.class);
	
	private final String PROFILE_IMAGE_NAME = "profileImage.jpg";
	
	@Autowired
	private SessionService sessionService;
	
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
    			logger.warn("~~~~~ Upload file to Amazon S3 failed, original file name: <{}>, Amazon S3 path and name: <{}>, error-msg: <{}>", originalFileName, pathAndName, e.getMessage(), e);
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
				logger.error("~~~~~ Upload file to Dropbox failed, original file name: <{}>, Dropbox path and name: <{}>, error-msg: <{}>", originalFileName, pathAndName, e.getMessage(), e);
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
				
				logger.info("<<<<< Delete file from Amazon S3 succeed, Amazon S3 path and name: <{}>", pathAndName);
				
				cloudStorage = CloudStorage.AMAZON_S3;
				deleteFromAmazonS3Succeed = true;
			}
			catch (Exception e) {
				amazonS3Service.setDisable();
				amazonS3Service.setErrorMsg(e.getMessage());
				logger.error("~~~~~ Delete file from Amazon S3 failed, Amazon S3 path and name: <{}>, try to delete from Dropbox, error-msg: <{}>", pathAndName, e.getMessage(), e);
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
				logger.error("~~~~~ Delete file from dropbox failed, Dropbox path and name: <{}>, error-msg: <{}>", pathAndName, e.getMessage(), e);
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
		User user = sessionService.getLoginUser();
		return user.getEmail() + "/" + PROFILE_IMAGE_NAME;
	}
	
	/**
	 * <pre>
	 * 從雲端空間取得大頭照
	 * </pre>
	 * 
	 * @param folderAndName
	 * 
	 * @return
	 */
	public ResponseEntity<byte[]> getProfileImage() {
		String pathAndName = getProfileImagePathAndName();
		ResponseEntity<byte[]> profileImage = downloadFile(pathAndName);
		return profileImage;
	}

	/**
     * <pre>
     * 從 Amazon S3 或 Dropbox 下載檔案
     * </pre>
     * 
     * @param pathAndName 欲下載檔案的路徑
     * 
     * @return 檔案內容
     */
	public ResponseEntity<byte[]> downloadFile(String pathAndName) {
		ResponseEntity<byte[]> file = null;
		
		boolean downloadFromAmazonS3Succeed;
		if (amazonS3Service.isEnable()) {
			try {
				logger.info(">>>>> Trying to download file from Amazon S3, Amazon S3 path and name: <{}>", pathAndName);
			
				file = downloadFileFromAmazonS3(pathAndName);
			
				logger.info(">>>>> Download file from Amazon S3 succeed, Amazon S3 path and name: <{}>", pathAndName);
			
				downloadFromAmazonS3Succeed = true;
			}
			catch (Exception e) {
				amazonS3Service.setDisable();
				amazonS3Service.setErrorMsg(e.getMessage());
				logger.warn("~~~~~ Download file from Amazon S3 failed, Amazon S3 path and name: <{}>, error-msg: <{}>", pathAndName, e.getMessage());
				downloadFromAmazonS3Succeed = false;
			}
		}
		else {
			logger.warn("~~~~~ Amazon S3 service is not available, error-msg: <{}>", amazonS3Service.getErrorMsg());
			downloadFromAmazonS3Succeed = false;
		}
		if (!downloadFromAmazonS3Succeed) {
			try {
				logger.info(">>>>> Trying to download file from Dropbox, Dropbox path and name: <{}>", pathAndName);
				
				file = downloadFileFromDropbox(pathAndName);
				
				logger.info("<<<<< Download file from Dropbox succeed, Dropbox path and name: <{}>", pathAndName);
				
			} catch (Exception e) {
				logger.error("~~~~~ Download file from Dropbox failed, Dropbox path and name: <{}>, error-msg: <{}>", pathAndName, e.getMessage(), e);
				throw new OperationException(ResultCode.DOWNLOAD_FILE_FAILED);
			}
		}
		return file;
	}

	/**
	 * <pre>
	 * 從 Amazon S3 取得檔案
	 * </pre>
	 * 
	 * @param pathAndName 欲取得在 Amazon S3 的檔案路徑
	 * 
	 * @return ResponseEntity<byte[]> 檔案內容
	 * 
	 * @throws Exception 
	 */
	private ResponseEntity<byte[]> downloadFileFromAmazonS3(String pathAndName) throws Exception {
		ResponseEntity<byte[]> file = null;
		
		long startTime = System.currentTimeMillis();
		logger.info("----> Prepare to download file from Amazon S3, Amazon S3 path and name: <{}>", pathAndName);
		
		file = amazonS3Service.download(pathAndName);
		
		long timeSpent = System.currentTimeMillis() - startTime;
		logger.info("<---- Download file from Amazon S3 done, time-spent: <{} ms>", timeSpent);
		
		return file;
	}
	
	/**
	 * <pre>
	 * 從 Dropbox 取得檔案
	 * </pre>
	 * 
	 * @param pathAndName 欲取得在 Dropbox 的檔案路徑
	 * 
	 * @return ResponseEntity<byte[]> 檔案內容
	 * 
	 * @throws Exception 
	 */
	private ResponseEntity<byte[]> downloadFileFromDropbox(String pathAndName) throws Exception {
		ResponseEntity<byte[]> file = null;
		
		long startTime = System.currentTimeMillis();
		logger.info("----> Prepare to download file from Dropbox, Dropbox path and name: <{}>", pathAndName);
		
		file = dropboxService.download(pathAndName);
		
		long timeSpent = System.currentTimeMillis() - startTime;
		logger.info("<---- Download file from Dropbox done, time-spent: <{} ms>", timeSpent);
		
		return file;
	}

	/**
	 * <pre>
	 * 列出登入者雲端空間的檔案
	 * </pre>
	 * 
	 * @param email 登入者的 email 
	 */
	public List<ListFileResp> listFiles(String email) {
		List<ListFileResp> results = new ArrayList<>();
		
		boolean listFromAmazonS3Succeed;
		if (amazonS3Service.isEnable()) {
			try {
				logger.info(">>>>> Trying to list file from Amazon S3, Amazon S3 path: <{}>", email);
			
				List<S3ObjectSummary> listFiles = amazonS3Service.listFiles(email);
				for (S3ObjectSummary file : listFiles) {
					ListFileResp listFileResp = new ListFileResp();
					listFileResp.setCloudStorage(CloudStorage.AMAZON_S3);
					listFileResp.setPathAndName(file.getKey());
					listFileResp.setFileSizeBytes(file.getSize());
					listFileResp.setLastModified(file.getLastModified());
					results.add(listFileResp);
				}
				
				logger.info(">>>>> List file from Amazon S3 succeed, Amazon S3 path: <{}>", email);
			
				listFromAmazonS3Succeed = true;
			}
			catch (Exception e) {
				amazonS3Service.setDisable();
				amazonS3Service.setErrorMsg(e.getMessage());
				logger.warn("~~~~~ List file from Amazon S3 failed, Amazon S3 path: <{}>, error-msg: <{}>", email, e.getMessage());
				listFromAmazonS3Succeed = false;
			}
		}
		else {
			logger.warn("~~~~~ Amazon S3 service is not available, error-msg: <{}>", amazonS3Service.getErrorMsg());
			listFromAmazonS3Succeed = false;
		}
		if (!listFromAmazonS3Succeed) {
			try {
				logger.info(">>>>> Trying to list file from Dropbox, Dropbox path: <{}>", email);
				
				List<FileMetadata> fileMetadatas = dropboxService.listFiles(email);
				for (FileMetadata fileMetadata : fileMetadatas) {
					ListFileResp listFileResp = new ListFileResp();
					listFileResp.setCloudStorage(CloudStorage.DROPBOX);
					listFileResp.setPathAndName(fileMetadata.getPathDisplay());
					listFileResp.setFileSizeBytes(fileMetadata.getSize());
					listFileResp.setLastModified(fileMetadata.getServerModified());
					results.add(listFileResp);
				}
				
				logger.info("<<<<< List file from Dropbox succeed, Dropbox path: <{}>", email);
				
			} catch (Exception e) {
				logger.error("~~~~~ List file from Dropbox failed, Dropbox path: <{}>, error-msg: <{}>", email, e.getMessage(), e);
				throw new OperationException(ResultCode.LIST_FILE_FAILED);
			}
		}
		return results;
	}
}
