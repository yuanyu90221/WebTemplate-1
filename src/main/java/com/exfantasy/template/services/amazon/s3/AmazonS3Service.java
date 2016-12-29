package com.exfantasy.template.services.amazon.s3;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

/**
 * <pre>
 * Amazon S3 服務
 * 
 * <a href="http://stackoverflow.com/questions/38578937/spring-boot-amazon-aws-s3-bucket-file-download-access-denied">此支 class 寫法參考</a>
 *
 * <a href="https://devcenter.heroku.com/articles/using-amazon-s3-for-file-uploads-with-java-and-play-2">Using Amazon S3 for file uploads</a>
 * 
 * <a href="http://stackoverflow.com/questions/36201759/how-to-set-inputstream-content-length">InputStream content length</a>
 * 
 * </pre>
 * 
 * @author tommy.feng
 *
 */
@Service
public class AmazonS3Service {
	
	private final Logger logger = LoggerFactory.getLogger(AmazonS3Service.class);

	@Autowired
	private AmazonS3Client amazonS3Client;

	@Value("${aws.s3.bucket}")
	private String bucket;

	private PutObjectResult upload(InputStream inputStream, String folderAndName) throws IOException {
		ObjectMetadata metadata = new ObjectMetadata();
		byte[] bytes = IOUtils.toByteArray(inputStream);
		metadata.setContentLength(bytes.length);

		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

		PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, folderAndName, byteArrayInputStream, metadata);

		putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);

		PutObjectResult putObjectResult = amazonS3Client.putObject(putObjectRequest);

		IOUtils.closeQuietly(inputStream);

		return putObjectResult;
	}

	public PutObjectResult upload(MultipartFile multipartFile, String folderAndName) throws IOException {
		return upload(multipartFile.getInputStream(), folderAndName);
	}

	public List<PutObjectResult> upload(MultipartFile[] multipartFiles) {
		List<PutObjectResult> putObjectResults = new ArrayList<>();

		Arrays.stream(multipartFiles)
			  .filter(multipartFile -> !StringUtils.isEmpty(multipartFile.getOriginalFilename()))
			  .forEach(multipartFile -> {
				  	// FIXME 再看看要怎麼調的好
					String folderAndName = multipartFile.getOriginalFilename();
					try {
						PutObjectResult putObjectResult 
							= upload(multipartFile.getInputStream(), folderAndName);
						
						putObjectResults.add(putObjectResult);

					} catch (IOException e) {
						logger.error("IOException raised while upload file to Amazon S3, folderAndName: <{}>", folderAndName, e);
					}
				}
			  );

		return putObjectResults;
	}

	public void deleteFile(String filePath) {
		amazonS3Client.deleteObject(bucket, filePath);
	}
}
