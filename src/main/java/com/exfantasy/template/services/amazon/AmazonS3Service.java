package com.exfantasy.template.services.amazon;

import org.springframework.stereotype.Service;

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
	
//	private final Logger logger = LoggerFactory.getLogger(AmazonS3Service.class);
//
//	@Autowired
//	private AmazonS3Client amazonS3Client;
//
//	@Value("${aws.s3.bucket}")
//	private String bucket;
//	
//	// FIXME 先關掉, 不然要付 15w 的錢
//	private boolean isEnable = false;
//	
//	private String errorMsg;
//
//	/**
//	 * <pre>
//	 * 上傳檔案到 Amazon S3
//	 * </pre>
//	 * 
//	 * @param inputStream
//	 * @param folderAndName 欲儲存 Bucket 的路徑
//	 * @return
//	 * @throws IOException
//	 */
//	private PutObjectResult upload(InputStream inputStream, String folderAndName) throws IOException {
//		logger.info(">>>>> Trying to upload file to Amazon S3, bucket: <{}>, folderAndName: <{}>", bucket, folderAndName);
//		
//		ObjectMetadata metadata = new ObjectMetadata();
//		byte[] bytes = IOUtils.toByteArray(inputStream);
//		metadata.setContentLength(bytes.length);
//
//		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
//
//		PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, folderAndName, byteArrayInputStream, metadata);
//
//		putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
//
//		PutObjectResult putObjectResult = amazonS3Client.putObject(putObjectRequest);
//
//		IOUtils.closeQuietly(inputStream);
//		
//		logger.info(">>>>> Upload file to Amazon S3 succeed, bucket: <{}>, folderAndName: <{}>", bucket, folderAndName);
//
//		return putObjectResult;
//	}
//	
//	public void setDisable() {
//		this.isEnable = false;
//	}
//	
//	public boolean isEnable() {
//		return this.isEnable;
//	}
//	
//	public void setErrorMsg(String errorMsg) {
//		this.errorMsg = errorMsg;
//	}
//	
//	public String getErrorMsg() {
//		return this.errorMsg;
//	}
//
//	/**
//	 * <pre>
//	 * 上傳單一檔案到 Amazon S3 並指定路徑及檔名
//	 * </pre>
//	 * 
//	 * @param multipartFile 要上傳的檔案
//	 * @param pathAndName 指定上傳至 Amazon S3 存放路徑及檔名
//	 * @return 上傳結果
//	 * @throws IOException
//	 */
//	public PutObjectResult upload(MultipartFile multipartFile, String pathAndName) throws IOException {
//		return upload(multipartFile.getInputStream(), pathAndName);
//	}
//
//	/**
//	 * <pre>
//	 * 上傳多個檔案到 Amazon S3
//	 * </pre>
//	 * 
//	 * @param multipartFiles 要上傳的檔案
//	 * @param email 使用者 email, 這些檔案將會放到 Amazon S3 對應 email 目錄底下
//	 * @return 上傳結果
//	 */
//	public List<PutObjectResult> upload(MultipartFile[] multipartFiles, String email) {
//		List<PutObjectResult> putObjectResults = new ArrayList<>();
//
//		Arrays.stream(multipartFiles)
//			  .filter(multipartFile -> !StringUtils.isEmpty(multipartFile.getOriginalFilename()))
//			  .forEach(multipartFile -> {
//					String folderAndName = email + "/" + multipartFile.getOriginalFilename();
//					try {
//						PutObjectResult putObjectResult 
//							= upload(multipartFile.getInputStream(), folderAndName);
//						
//						putObjectResults.add(putObjectResult);
//
//					} catch (IOException e) {
//						logger.error("IOException raised while upload file to Amazon S3, folderAndName: <{}>", folderAndName, e);
//					}
//				}
//			  );
//
//		return putObjectResults;
//	}
//
//	/**
//	 * <pre>
//	 * 從 Amazon S3 刪除一個檔案
//	 * </pre>
//	 * 
//	 * @param pathAndName 指定想從 Amazon S3 刪除檔案的存放路徑及檔名
//	 */
//	public void deleteFile(String pathAndName) throws Exception {
//		amazonS3Client.deleteObject(bucket, pathAndName);
//	}
//	
//	/**
//	 * <pre>
//	 * 從 Amazon S3 指定 path 和 name 下載檔案
//	 * </pre>
//	 * 
//	 * @param pathAndName 位於 Amazon S3 的路徑
//	 * @return
//	 * @throws Exception
//	 */
//	public ResponseEntity<byte[]> download(String pathAndName) throws Exception {
//        GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, pathAndName);
//
//        long startTime = System.currentTimeMillis();
//        logger.info(">>>>> Trying to download file from Amazon S3, pathAndName: <{}>", pathAndName);
//        
//        S3Object s3Object = amazonS3Client.getObject(getObjectRequest);
//        
//        logger.info("<<<<< Download file from Amazon S3 succeed, pathAndName: <{}>, time-spent: <{} ms>", pathAndName, System.currentTimeMillis() - startTime);
//
//        S3ObjectInputStream objectInputStream = s3Object.getObjectContent();
//
//        byte[] bytes = IOUtils.toByteArray(objectInputStream);
//
//        HttpHeaders httpHeaders = FileUtil.getHttpHeaderByFileName(pathAndName, bytes);
//
//        return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
//    }
//
//	/**
//	 * <pre>
//	 * 列出 bucket 底下的所有目錄及檔案
//	 * </pre>
//	 * 
//	 * @return
//	 */
//	public List<S3ObjectSummary> listBucketFiles() {
//		long startTime = System.currentTimeMillis();
//        logger.info(">>>>> Trying to get list from Amazon S3, bucket: <{}>", bucket);
//		
//		ObjectListing objectListing = amazonS3Client.listObjects(new ListObjectsRequest().withBucketName(bucket));
//		
//		logger.info("<<<<< Get list from Amazon S3 succeed, bucket: <{}>, time-spent: <{} ms>", bucket, System.currentTimeMillis() - startTime);
//
//        List<S3ObjectSummary> s3ObjectSummaries = objectListing.getObjectSummaries();
//
//        return s3ObjectSummaries;
//	}
//	
//	/**
//	 * <pre>
//	 * 列出指定 folder 底下的所有目錄及檔案
//	 * </pre>
//	 * 
//	 * @param folder
//	 * @return
//	 */
//	public List<S3ObjectSummary> listFiles(String folder) {
//		long startTime = System.currentTimeMillis();
//		logger.info(">>>>> Trying to get list from Amazon S3, bucket: <{}>, marker: <{}>", bucket, folder);
//		
//		ObjectListing objectListing = amazonS3Client.listObjects(new ListObjectsRequest().withBucketName(bucket).withMarker(folder));
//		
//		logger.info(">>>>> Get list from Amazon S3, bucket: <{}>, marker: <{}>, time-spent: <{} ms>", bucket, folder, System.currentTimeMillis() - startTime);
//		
//		List<S3ObjectSummary> s3ObjectSummaries = objectListing.getObjectSummaries();
//		
//		return s3ObjectSummaries;
//	}
}
