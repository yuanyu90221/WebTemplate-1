package com.exfantasy.template.services.amazon.s3;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.exfantasy.template.cnst.ResultCode;
import com.exfantasy.template.config.AmazonS3Config;
import com.exfantasy.template.exception.OperationException;

/**
 * <pre>
 * Amazon S3 服務
 * 
 * <a href="https://devcenter.heroku.com/articles/using-amazon-s3-for-file-uploads-with-java-and-play-2">Using Amazon S3 for file uploads</a>
 * </pre>
 * 
 * @author tommy.feng
 *
 */
@Service
public class AmazonS3Service {
	private static final Logger logger = LoggerFactory.getLogger(AmazonS3Service.class);

	private AmazonS3 amazonS3;
	private String bucket;
	private boolean isEnabled;
	
	@Autowired
	public AmazonS3Service(AmazonS3Config config) {
		String accessKey = config.getAccessKey();
		String secretKey = config.getSecretKey();
		this.bucket = config.getBucket();

		if (accessKey != null && secretKey != null && bucket != null) {
			AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
			
			logger.info(">>>> Trying S3 with AccessKey: <{}>, SecretKey: <{}>", accessKey, secretKey);

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
	
	public void uploadFile(String filePath, File file) {
		if (isEnabled) {
			PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, filePath, file);
			putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
			amazonS3.putObject(putObjectRequest);
		}
		else {
			throw new OperationException(ResultCode.AMAZON_S3_IS_NOT_ENABLED);
		}
	}
	
	public void deleteFile(String filePath) {
		amazonS3.deleteObject(bucket, filePath);
	}
}
