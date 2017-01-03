package com.exfantasy.template.services.amazon;

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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;

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
	
	private boolean isEnable = true;
	
	private String errorMsg;

	private PutObjectResult upload(InputStream inputStream, String folderAndName) throws IOException {
		logger.info(">>>>> Trying to upload file to Amazon S3, bucket: <{}>, folderAndName: <{}>", bucket, folderAndName);
		
		ObjectMetadata metadata = new ObjectMetadata();
		byte[] bytes = IOUtils.toByteArray(inputStream);
		metadata.setContentLength(bytes.length);

		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

		PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, folderAndName, byteArrayInputStream, metadata);

		putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);

		PutObjectResult putObjectResult = amazonS3Client.putObject(putObjectRequest);

		IOUtils.closeQuietly(inputStream);
		
		logger.info(">>>>> Upload file to Amazon S3 succeed, bucket: <{}>, folderAndName: <{}>", bucket, folderAndName);

		return putObjectResult;
	}
	
	public void setDisable() {
		this.isEnable = false;
	}
	
	public boolean isEnable() {
		return this.isEnable;
	}
	
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public String getErrorMsg() {
		return this.errorMsg;
	}

	/**
	 * <pre>
	 * 上傳單一檔案到 Amazon S3 並指定路徑及檔名
	 * </pre>
	 * 
	 * @param multipartFile 要上傳的檔案
	 * @param pathAndName 指定上傳至 Amazon S3 存放路徑及檔名
	 * @return 上傳結果
	 * @throws IOException
	 */
	public PutObjectResult upload(MultipartFile multipartFile, String pathAndName) throws IOException {
		return upload(multipartFile.getInputStream(), pathAndName);
	}

	/**
	 * <pre>
	 * 上傳多個檔案到 Amazon S3
	 * </pre>
	 * 
	 * @param multipartFiles 要上傳的檔案
	 * @param email 使用者 email, 這些檔案將會放到 Amazon S3 對應 email 目錄底下
	 * @return 上傳結果
	 */
	public List<PutObjectResult> upload(MultipartFile[] multipartFiles, String email) {
		List<PutObjectResult> putObjectResults = new ArrayList<>();

		Arrays.stream(multipartFiles)
			  .filter(multipartFile -> !StringUtils.isEmpty(multipartFile.getOriginalFilename()))
			  .forEach(multipartFile -> {
					String folderAndName = email + "/" + multipartFile.getOriginalFilename();
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

	/**
	 * <pre>
	 * 從 Amazon S3 刪除一個檔案
	 * </pre>
	 * 
	 * @param pathAndName 指定想從 Amazon S3 刪除檔案的存放路徑及檔名
	 */
	public void deleteFile(String pathAndName) throws Exception {
		amazonS3Client.deleteObject(bucket, pathAndName);
	}
	
	public ResponseEntity<byte[]> download(String pathAndName) throws Exception {
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, pathAndName);

        long startTime = System.currentTimeMillis();
        logger.info(">>>> Trying to download file from Amazon S3, pathAndName: <{}>", pathAndName);
        
        S3Object s3Object = amazonS3Client.getObject(getObjectRequest);
        
        logger.info("<<<< Download file from Amazon S3 succeed, pathAndName: <{}>, time-spent: <{} ms>", pathAndName, System.currentTimeMillis() - startTime);

        S3ObjectInputStream objectInputStream = s3Object.getObjectContent();

        byte[] bytes = IOUtils.toByteArray(objectInputStream);

//        String fileName = URLEncoder.encode(folderAndName, "UTF-8").replaceAll("\\+", "%20");
//        String fileName = "profileImage.jpg";

        // http://stackoverflow.com/questions/5690228/spring-mvc-how-to-return-image-in-responsebody
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.IMAGE_JPEG);
        httpHeaders.setContentLength(bytes.length);
//        httpHeaders.setContentDispositionFormData("attachment", fileName);

        return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
    }
	
	public List<S3ObjectSummary> list() {
		long startTime = System.currentTimeMillis();
        logger.info(">>>> Trying to get list from Amazon S3, bucket: <{}>", bucket);
		
		ObjectListing objectListing = amazonS3Client.listObjects(new ListObjectsRequest().withBucketName(bucket));
		
		logger.info(">>>> Get list from Amazon S3 succeed, bucket: <{}>, time-spent: <{} ms>", bucket, System.currentTimeMillis() - startTime);

        List<S3ObjectSummary> s3ObjectSummaries = objectListing.getObjectSummaries();

        return s3ObjectSummaries;
	}
}
