package com.exfantasy.template.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;

/**
 * <pre>
 * 關於 Amazon S3 的設定
 * 
 * <a href="http://stackoverflow.com/questions/38578937/spring-boot-amazon-aws-s3-bucket-file-download-access-denied">參考網址</a>
 * </pre>
 * 
 * @author tommy.feng
 *
 */
@Configuration
public class AmazonS3Config {
	private static final Logger logger = LoggerFactory.getLogger(AmazonS3Config.class);

	@Value("${aws.s3.accessKey}")
	private String accessKey;

	@Value("${aws.s3.secretKey}")
	private String secretKey;

	@Bean
	public BasicAWSCredentials basicAWSCredentials() {
		logger.info(">>>>> Amazon S3 access key: <{}>", accessKey);
		logger.info(">>>>> Amazon S3 secret key: <{}>", secretKey);
		return new BasicAWSCredentials(accessKey, secretKey);
	}

	@Bean
	public AmazonS3Client amazonS3Client(AWSCredentials awsCredentials) {
		AmazonS3Client amazonS3Client = new AmazonS3Client(awsCredentials);
		// FIXME 看看換個區域這東西有沒有用
//		amazonS3Client.setRegion(Region.getRegion(Regions.fromName(region)));
		return amazonS3Client;
	}
}