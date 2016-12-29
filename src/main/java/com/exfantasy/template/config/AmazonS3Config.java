package com.exfantasy.template.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonS3Config {

	@Value("${aws.s3.bucket}")
	private String bucket;

	@Value("${aws.s3.accessKey}")
	private String accessKey;
	
	@Value("${aws.s3.secretKey}")
	private String secretKey;

	public String getBucket() {
		return bucket;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public String getSecretKey() {
		return secretKey;
	}
	
}