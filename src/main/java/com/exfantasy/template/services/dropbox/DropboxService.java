package com.exfantasy.template.services.dropbox;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.DeleteErrorException;
import com.dropbox.core.v2.files.ListFolderErrorException;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.files.UploadErrorException;
import com.dropbox.core.v2.users.FullAccount;

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
	
	public void upload(MultipartFile multipartFile, String pathAndName) throws UploadErrorException, DbxException, IOException {
		if (!pathAndName.startsWith("/")) {
			pathAndName = "/" + pathAndName;	
		}
		upload(multipartFile.getInputStream(), pathAndName);
	}
	
	private void upload(InputStream inputStream, String pathAndName) throws UploadErrorException, DbxException, IOException {
		boolean alreadyContains = alreadyContains(pathAndName);
		if (alreadyContains) {
			logger.warn("----> Discover that the file is already existed, path and name: <{}>, try to delete it", pathAndName);
			delete(pathAndName);
			logger.warn("<---- Delete the existed file succeed, path and name: <{}>", pathAndName);
		}
		dropboxClient.files().uploadBuilder(pathAndName).uploadAndFinish(inputStream);
	}
	
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
	
	public void delete(String pathAndName) throws DeleteErrorException, DbxException {
		if (!pathAndName.startsWith("/")) {
			pathAndName = "/" + pathAndName;	
		}
		dropboxClient.files().delete(pathAndName);
	}

	public ResponseEntity<byte[]> download(String pathAndName) {
		// TODO Dropbox download file
		return null;
	}
}
