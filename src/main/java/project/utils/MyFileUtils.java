package project.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Component
public class MyFileUtils {
	
	/* 230106 한아 작성 */
	/* 20230110 문대현 수정 */

	@Value("${cloud.aws.s3.fileupload.bucket}")
	private String bucket;
	
	@Autowired
	private AmazonS3Client amazonS3Client;
	
	/**
	 * @param uploadPath S3내 이미지가 업로드 될 경로
	 * @param key S3내 Temp 이미지 파일 경로
	 * @param newName 저장될 이미지 이름
	 * @return S3내 저장된 이미지 경로 반환(경로+파일이름)(해당 url로 외부에서 접근 가능)
	 */
	public String upload(String uploadPath, String key,String newName) {
		
		String newKey = uploadPath+newName;
		amazonS3Client.copyObject(this.bucket, key, this.bucket, newKey);
		amazonS3Client.deleteObject(this.bucket,key);
		String url = amazonS3Client.getUrl(this.bucket, uploadPath+newName).toString();
		System.err.println("업로드 >>>>> " +  httpsToHttp(url));
		return httpsToHttp(url);
	}
	
	/**
	 * @param path : 저장할 bucket 하위 폴더의 경로 (ex : temp/)
	 * @param multipartFile : 저장할 이미지 파일
	 * @return Map 형식으로(타입 String) OrgName, NewName, Url 정보 리턴
	 */
	public Map<String, String> store(String path, MultipartFile multipartFile) {

		String orgName = multipartFile.getOriginalFilename();
		String newName = createNewName(orgName);

		String imgkey = path + newName;

		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(multipartFile.getContentType());
		objectMetadata.setContentLength(multipartFile.getSize());

		PutObjectRequest putObjectRequest;
		try {
			putObjectRequest = new PutObjectRequest(bucket, imgkey, multipartFile.getInputStream(), objectMetadata);
			amazonS3Client.putObject(putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead));
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("오류발생");
		}

		Map<String, String> tempFileInfo = new HashMap<>();
		
		String url = httpsToHttp(amazonS3Client.getUrl(bucket, imgkey).toString());
		
		System.err.println("임시파일 업로드 >>>>>" + url);
		
		tempFileInfo.put("url", url);
		tempFileInfo.put("key", imgkey);
		tempFileInfo.put("oldName", orgName);
		tempFileInfo.put("newName", newName);

		return tempFileInfo;
	}
	
	/**
	 * @param orgName 변경할 파일의 원본이름
	 * @return 원본이름_UUID.확장자 <- String 타입으로 반환
	 */
	private String createNewName(String orgName) {
		int idx = orgName.lastIndexOf(".");
		String newName = orgName.substring(0, idx) + "_" + UUID.randomUUID().toString() + orgName.substring(idx);
		return newName;
	}
	
	/**
	 * @param url 아마존 외부에서 접근가능한 https 타입의 url주소
	 * @return https -> http로 변경후 반환
	 */
	private String httpsToHttp(String url) {
		int colon = url.lastIndexOf(":");
		String result = url.substring(colon);
		String newURL = "http"+result;
		return newURL;
	}

}
