package com.exfantasy.template.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

/**
 * <pre>
 * 關於 Dropbox 的設定
 * 
 * <a href="https://www.dropbox.com/developers/documentation/java#tutorial">Dropbox Java Tutorial</a>
 * </pre>
 * 
 * @author tommy.feng
 *
 */
@Configuration
public class DropboxConfig {

	@Value("${dropbox.accessToken}")
	private String dropBoxAccessToken;
	
	private final String clientIdentifier = "TommyWebService";
	
	@Bean
	public DbxRequestConfig dropboxRequestConfig() {
		return DbxRequestConfig.newBuilder(clientIdentifier).build();
	}

	@Bean
	public DbxClientV2 dropboxClientV2(DbxRequestConfig config) {
		return new DbxClientV2(config, dropBoxAccessToken);
	}
}
