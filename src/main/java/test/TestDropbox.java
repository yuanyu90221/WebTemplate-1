package test;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.users.FullAccount;

public class TestDropbox {
	public static void main(String[] args) throws DbxException {
		if (args.length != 1) {
			System.err.println("Please give a Program aruguments as DropBox access token");
			System.exit(1);
		}
		String ACCESS_TOKEN = args[0];
		DbxRequestConfig config = DbxRequestConfig.newBuilder("TommyTest").build();
		DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
		FullAccount account = client.users().getCurrentAccount();
		System.out.println(account.getName().getDisplayName());
		System.out.println(account.getEmail());
	}
}
