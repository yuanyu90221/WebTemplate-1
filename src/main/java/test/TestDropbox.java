package test;

import java.io.IOException;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.ListFolderErrorException;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;

public class TestDropbox {
	private DbxClientV2 client;
	
	public TestDropbox(String accessToken) {
		DbxRequestConfig config = DbxRequestConfig.newBuilder("TommyTest").build();
		client = new DbxClientV2(config, accessToken);
	}

	private void start() throws DbxException, IOException {
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

	public static void main(String[] args) throws DbxException, IOException {
		if (args.length != 1) {
			System.err.println("Please give a Program aruguments as DropBox access token");
			System.exit(1);
		}
		String accessToken = args[0];
		new TestDropbox(accessToken).start();
	}
}
