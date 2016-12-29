package com.exfantasy.template.services.amazon.s3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.exfantasy.template.config.AmazonS3Config;

@Service
public class AmazonS3Service {
	private static final Logger logger = LoggerFactory.getLogger(AmazonS3Service.class);

	private AmazonS3 amazonS3;
	private boolean isEnabled;
	
	@Autowired
	public AmazonS3Service(AmazonS3Config config) {
		String accessKey = config.getAccessKey();
		String secretKey = config.getSecretKey();
		String bucket = config.getBucket();

		if (accessKey != null && secretKey != null && bucket != null) {
			AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
			amazonS3 = new AmazonS3Client(awsCredentials);
			amazonS3.createBucket(bucket);
		
			logger.info(">>>>> Using S3 Bucket: {}", bucket);
			
			isEnabled = true;
		}
		else {
			logger.warn(">>>>> Cannot find Amazon S3 config setting, won't initialize AmazonS3 Service");
			
			isEnabled = false;
		}
	}
}
