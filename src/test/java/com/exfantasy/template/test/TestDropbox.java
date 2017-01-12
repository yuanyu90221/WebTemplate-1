package com.exfantasy.template.test;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.ListFolderErrorException;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
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
	public void start() throws ListFolderErrorException, DbxException {
		getAllFileOfSpecificdFolder("/tommy.yeh1112@gmail.com");
		
		System.out.print("\n\n");
		
		getAllFoldersAndFiles();
		
		System.out.print("\n\n");
		
		getContentsOfRootFolders();
	}

	private void getAllFileOfSpecificdFolder(String folder) throws ListFolderErrorException, DbxException {
		System.out.println("===== Try to get all files of specificed folder  =====");
		
		ListFolderResult result = client.files().listFolder(folder);
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

	private void getAllFoldersAndFiles() throws ListFolderErrorException, DbxException {
		System.out.println("===== Try to get all folders and files =====");
		
		ListFolderResult result = client.files().listFolderBuilder("").withRecursive(true).start();
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

	private void getContentsOfRootFolders() throws ListFolderErrorException, DbxException {
		System.out.println("===== Try to get contents of root folders =====");
			
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
}
