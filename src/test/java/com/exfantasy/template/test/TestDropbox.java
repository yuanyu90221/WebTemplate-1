package com.exfantasy.template.test;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.users.FullAccount;
import com.exfantasy.template.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(
	classes = Application.class,
	webEnvironment = WebEnvironment.RANDOM_PORT
)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestDropbox {

	@Autowired
	private DbxClientV2 client;
	
	@Test
	public void test_1_showAccountInfomation() throws Exception {
		System.out.println("===== Try to get Dropbox account information =====");
		
		FullAccount account = client.users().getCurrentAccount();
		
		System.out.printf("Account Id: <%s>\n", account.getAccountId());
		System.out.printf("Account type: <%s>\n", account.getAccountType());
		System.out.printf("Country: <%s>\n", account.getCountry());
		System.out.printf("Email: <%s>\n", account.getEmail());
		System.out.printf("Locale: <%s>\n", account.getLocale());
		System.out.printf("Account name: <%s>\n", account.getName().getDisplayName());
		System.out.printf("ProfilePhotoUrl: <%s>\n", account.getProfilePhotoUrl());
		System.out.printf("ReferralLink: <%s>\n", account.getReferralLink());
	}
	
	@Test
	public void test_2_getAllFileOfSpecificdFolder() throws Exception {
		final String folder = "/tommy.yeh1112@gmail.com";
		
		System.out.println("===== Try to get all files of folder: <" + folder + ">  =====");
		
		ListFolderResult result = client.files().listFolder(folder);
		while (true) {
			for (Metadata metadata : result.getEntries()) {
				System.out.println("Name: " + metadata.getName());
		    	System.out.println("ParentSharedFolderId: " + metadata.getParentSharedFolderId());
		    	System.out.println("PathDisplay: " + metadata.getPathDisplay());
		        System.out.println("PathLower: " + metadata.getPathLower());
		        System.out.println("-   -   -   -   -   -   -   -   -   -   -   -   -   -");
			}
			
			if (!result.getHasMore()) {
		        break;
		    }
			
			result = client.files().listFolderContinue(result.getCursor());
		}
	}

	@Test
	public void test_3_getAllFoldersAndFiles() throws Exception {
		System.out.println("===== Try to get all folders and files =====");
		
		ListFolderResult result = client.files().listFolderBuilder("").withRecursive(true).start();
		while (true) {
			for (Metadata metadata : result.getEntries()) {
				System.out.println("Name: " + metadata.getName());
		    	System.out.println("ParentSharedFolderId: " + metadata.getParentSharedFolderId());
		    	System.out.println("PathDisplay: " + metadata.getPathDisplay());
		        System.out.println("PathLower: " + metadata.getPathLower());
		        System.out.println("-   -   -   -   -   -   -   -   -   -   -   -   -   -");
			}
			
			if (!result.getHasMore()) {
		        break;
		    }
			
			result = client.files().listFolderContinue(result.getCursor());
		}
	}

	@Test
	public void test_4_getContentsOfRootFolders() throws Exception {
		System.out.println("===== Try to get contents of root folders =====");
			
		ListFolderResult result = client.files().listFolder("");
		while (true) {
		    for (Metadata metadata : result.getEntries()) {
		    	System.out.println("Name: " + metadata.getName());
		    	System.out.println("ParentSharedFolderId: " + metadata.getParentSharedFolderId());
		    	System.out.println("PathDisplay: " + metadata.getPathDisplay());
		        System.out.println("PathLower: " + metadata.getPathLower());
		        System.out.println("-   -   -   -   -   -   -   -   -   -   -   -   -   -");
		    }

		    if (!result.getHasMore()) {
		        break;
		    }

		    result = client.files().listFolderContinue(result.getCursor());
		}
	}
}
