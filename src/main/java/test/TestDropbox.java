package test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderErrorException;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.files.UploadErrorException;
import com.dropbox.core.v2.users.FullAccount;

public class TestDropbox {
	private DbxClientV2 client;
	
	public TestDropbox(String accessToken) {
		DbxRequestConfig config = DbxRequestConfig.newBuilder("TommyTest").build();
		client = new DbxClientV2(config, accessToken);
	}

	private void start() throws DbxException, IOException {
		getAccountInformation();
		
		tryingUpload();

		getContentsOfFolder();
	}

	private void tryingUpload() throws UploadErrorException, DbxException, IOException {
		System.out.println("------ Try to upload file to DropBox ------");
		System.out.println(">>>>> Try to upload file to DropBox");
		try (InputStream in = new FileInputStream("D:/Test.txt")) {
			FileMetadata meradata = client.files().uploadBuilder("/Test.txt").uploadAndFinish(in);
		}
		System.out.println("<<<<< Upload file to dropbox done");
	}

	private void getAccountInformation() throws DbxException {
		System.out.println("------ Try to get DropBox account information ------");
		
		FullAccount account = client.users().getCurrentAccount();
		System.out.println("Account Id: " + account.getAccountId());
		System.out.println("Account type: " + account.getAccountType());
		System.out.println("Country: " + account.getCountry());
		System.out.println("Email: " + account.getEmail());
		System.out.println("Locale: " + account.getLocale());
		System.out.println("Account name: " + account.getName().getDisplayName());
		System.out.println("ProfilePhotoUrl: " + account.getProfilePhotoUrl());
		System.out.println("ReferralLink: " + account.getReferralLink());
	}
	
	private void getContentsOfFolder() throws ListFolderErrorException, DbxException {
		System.out.println("------- Try to get contents of folder -------");
		
		ListFolderResult result = client.files().listFolder("");
		while (true) {
		    for (Metadata metadata : result.getEntries()) {
		    	System.out.println("Name: " + metadata.getName());
		    	System.out.println("ParentSharedFolderId: " + metadata.getParentSharedFolderId());
		    	System.out.println("PathDisplay: " + metadata.getPathDisplay());
		        System.out.println("PathLower: " + metadata.getPathLower());
		        System.out.println("-   -   -   -   -   -   -   -   -   -");
		    }

		    if (!result.getHasMore()) {
		        break;
		    }

		    result = client.files().listFolderContinue(result.getCursor());
		}
	}

	public static void main(String[] args) throws DbxException, IOException {
		if (args.length != 1) {
			System.err.println("Please give a Program aruguments as DropBox access token");
			System.exit(1);
		}
		String accessToken = args[0];
		new TestDropbox(accessToken).start();
	}
}
