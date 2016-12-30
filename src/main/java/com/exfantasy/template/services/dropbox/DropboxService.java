package com.exfantasy.template.services.dropbox;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.DeleteErrorException;
import com.dropbox.core.v2.files.UploadErrorException;
import com.dropbox.core.v2.users.FullAccount;

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
		dropboxClient.files().uploadBuilder(pathAndName).uploadAndFinish(inputStream);
	}
	
	public void delete(String pathAndName) throws DeleteErrorException, DbxException {
		if (!pathAndName.startsWith("/")) {
			pathAndName = "/" + pathAndName;	
		}
		dropboxClient.files().delete(pathAndName);
	}
}
