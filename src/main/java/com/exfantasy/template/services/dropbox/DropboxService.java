package com.exfantasy.template.services.dropbox;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.DeleteErrorException;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderErrorException;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.files.UploadErrorException;
import com.dropbox.core.v2.users.FullAccount;
import com.exfantasy.template.util.FileUtil;

/**
 * <pre>
 * 對 Dropbox 進行一些操作
 * 
 * 參考: <a href="https://www.dropbox.com/developers/documentation/java#tutorial">Dropbox Java Tutorial</a>
 * </pre>
 * @author tommy.feng
 *
 */
@Service
public class DropboxService {
	private static final Logger logger = LoggerFactory.getLogger(DropboxService.class);
	
	@Autowired
    private DbxClientV2 dropboxClient;
	
	/**
	 * <pre>
	 * 取得 Dropbox 帳號資訊
	 * </pre>
	 * 
	 * @return String Dropbox 帳號資訊
	 */
	public String getAccountInformation() {
		StringBuilder buffer = new StringBuilder();
		try {
			FullAccount account = dropboxClient.users().getCurrentAccount();
			buffer.append("Account Id: <").append(account.getAccountId()).append(">, ");
			buffer.append("Account type: <").append(account.getAccountType()).append(">, ");
			buffer.append("Country: <").append(account.getCountry()).append(">, ");
			buffer.append("Email: <").append(account.getEmail()).append(">, ");
			buffer.append("Locale: <").append(account.getLocale()).append(">, ");
			buffer.append("Account name: <").append(account.getName().getDisplayName()).append(">, ");
			buffer.append("ProfilePhotoUrl: <").append(account.getProfilePhotoUrl()).append(">, ");
			buffer.append("ReferralLink: <").append(account.getReferralLink()).append(">");
			String dropboxAccountInformation = buffer.toString();
			logger.info("Dropbox Account Information -> {}", dropboxAccountInformation);
			return dropboxAccountInformation;
		} catch (DbxException e) {
			String errorMsg = "DbxException while getting dropbox account information, msg: " + e.getMessage();
			logger.error(errorMsg, e);
			return errorMsg;
		}
	}
	
	/**
	 * <pre>
	 * 上傳檔案到 Dropbox 指定目錄及路徑
	 * </pre>
	 * 
	 * @param multipartFile 欲上傳的檔案
	 * @param pathAndName 欲儲存於 Dropbox 的路徑
	 * 
	 * @throws UploadErrorException
	 * @throws DbxException
	 * @throws IOException
	 */
	public void upload(MultipartFile multipartFile, String pathAndName) throws UploadErrorException, DbxException, IOException {
		if (!pathAndName.startsWith("/")) {
			pathAndName = "/" + pathAndName;	
		}
		upload(multipartFile.getInputStream(), pathAndName);
	}
	
	/**
	 * <pre>
	 * 上傳檔案到 Dropbox 指定目錄及路徑
	 * </pre>
	 * 
	 * @param inputStream file 取得的 inputStream
	 * @param pathAndName 欲儲存於 Dropbox 的路徑
	 * 
	 * @throws UploadErrorException
	 * @throws DbxException
	 * @throws IOException
	 */
	private void upload(InputStream inputStream, String pathAndName) throws UploadErrorException, DbxException, IOException {
		boolean alreadyContains = alreadyContains(pathAndName);
		if (alreadyContains) {
			logger.warn("----> Discover that the file is already existed, path and name: <{}>, try to delete it", pathAndName);
			delete(pathAndName);
			logger.warn("<---- Delete the existed file succeed, path and name: <{}>", pathAndName);
		}
		dropboxClient.files().uploadBuilder(pathAndName).uploadAndFinish(inputStream);
	}
	
	/**
	 * <pre>
	 * 判斷指定的檔案路徑是否已存在
	 * </pre>
	 * 
	 * @param pathAndName 儲存於 Dropbox 的路徑
	 * @return boolean 是否已存在
	 * @throws ListFolderErrorException
	 * @throws DbxException
	 */
	private boolean alreadyContains(String pathAndName) throws ListFolderErrorException, DbxException {
		int lastIndexOf = pathAndName.lastIndexOf("/");
		String folderPath = pathAndName.substring(0, lastIndexOf);
		String folderName = folderPath.substring(1, folderPath.length());
		String fileName = pathAndName.substring(lastIndexOf + 1, pathAndName.length());
		
		boolean foundFolder = foundFolder(folderName);
		
		if (foundFolder) {
			ListFolderResult result = dropboxClient.files().listFolder(folderPath);
			for (Metadata metadata : result.getEntries()) {
				String existedFileName = metadata.getName();
				if (existedFileName.equals(fileName)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * <pre>
	 * 判斷目錄是否已存在 Dropbox
	 * </pre>
	 * 
	 * @param folderName 儲存於 Dropbox 的目錄名稱
	 * @return boolean 是否已存在
	 * @throws ListFolderErrorException
	 * @throws DbxException
	 */
	private boolean foundFolder(String folderName) throws ListFolderErrorException, DbxException {
		ListFolderResult result = dropboxClient.files().listFolder("");
		for (Metadata metadata : result.getEntries()) {
			String existedFolder = metadata.getName();
			if (existedFolder.equals(folderName)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * <pre>
	 * 刪除儲存於 Dropbox 的檔案
	 * </pre>
	 * 
	 * @param pathAndName 儲存於 Dropbox 的路徑
	 * @throws DeleteErrorException
	 * @throws DbxException
	 */
	public void delete(String pathAndName) throws DeleteErrorException, DbxException {
		if (!pathAndName.startsWith("/")) {
			pathAndName = "/" + pathAndName;	
		}
		dropboxClient.files().delete(pathAndName);
	}

	/**
	 * <pre>
	 * 從 Dropbox 下載檔案
	 * </pre>
	 * 
	 * @param pathAndName 儲存於 Dropbox 的路徑
	 * @return ResponseEntity<byte[]> 檔案
	 * @throws Exception
	 */
	public ResponseEntity<byte[]> download(String pathAndName) throws Exception {
		if (!pathAndName.startsWith("/")) {
			pathAndName = "/" + pathAndName;	
		}

		long startTime = System.currentTimeMillis();
        logger.info(">>>>> Trying to download file from Dropbox, pathAndName: <{}>", pathAndName);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
		@SuppressWarnings("unused")
		FileMetadata metedata = dropboxClient.files().download(pathAndName).download(baos);
		
		logger.info("<<<<< Download file from Dropbox succeed, pathAndName: <{}>, time-spent: <{} ms>", pathAndName, System.currentTimeMillis() - startTime);
		
		byte[] bytes = baos.toByteArray();

		HttpHeaders httpHeaders = FileUtil.getHttpHeaderByFileName(pathAndName, bytes);
		
		return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
	}

	/**
	 * <pre>
	 * 列出指定路徑 Dropbox 下所有檔案
	 * </pre>
	 * 
	 * @param path Dropbox 的某的路徑
	 * @return List<FileMetadata> 檔案資訊
	 * @throws Exception
	 */
	public List<FileMetadata> listFiles(String path) throws Exception {
		List<FileMetadata> fileMetadatas = new ArrayList<>();
		
		if (!path.startsWith("/")) {
			path = "/" + path;
		}
		
		long startTime = System.currentTimeMillis();
        logger.info(">>>>> Trying to list file from Dropbox, path: <{}>", path);
		
		ListFolderResult result = dropboxClient.files().listFolderBuilder(path).withIncludeMediaInfo(true).start();
		
		logger.info("<<<<< List file from Dropbox succeed, path: <{}>, time-spent: <{} ms>", path, System.currentTimeMillis() - startTime);
		
		for (Metadata metadata : result.getEntries()) {
			fileMetadatas.add((FileMetadata) metadata);
		}
		return fileMetadatas;
	}
}
