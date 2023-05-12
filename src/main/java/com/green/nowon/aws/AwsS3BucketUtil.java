package com.green.nowon.aws;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AwsS3BucketUtil {
	
	private final AmazonS3Client client;
	
	@Value("${cloud.aws.s3.file-upload-bucket}")
	private String bucket;
	
	@Value("${cloud.aws.s3.temp-path}")
	private String tempPath;
	
	@Value("${cloud.aws.s3.product-path}")
	private String productPath;
	
	//temp경로에 이미지 업로드
	public Map<String,String> tempUpload(MultipartFile mf) {
		Map<String,String> map=new HashMap<>();
		String tempKey=tempPath+newFileName(mf.getOriginalFilename());
		//System.out.println(">>>>tempKey : "+tempKey);
		try(InputStream is=mf.getInputStream()) {
			PutObjectRequest putObjectRequest=new PutObjectRequest(bucket, tempKey, is, objectMetadata(mf));
			client.putObject(putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead));
			//미리보기 경로
			map.put("s3TempUrl", client.getUrl(bucket, tempKey).toString().substring(6)); // https: 까지 제거
			map.put("orgName", mf.getOriginalFilename());
			map.put("tempKey", tempKey); //버킷 컨트롤 하기위한 정보
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	private String newFileName(String orgName) {
		int idx=orgName.lastIndexOf(".");
		return UUID.randomUUID().toString() //새로운이름을 UUID로 생성
				+orgName.substring(idx); //.확장자
	}
	private String newFileNameByNanotime(String orgName) {
		int idx=orgName.lastIndexOf(".");
		return orgName.substring(0,idx)+"_"+(System.nanoTime()/10000000)
				+orgName.substring(idx); //.확장자
	}
	//파일 설정 정보
	private ObjectMetadata objectMetadata(MultipartFile mf) {
		ObjectMetadata objectMetadata=new ObjectMetadata();
		//String contentType=tempFile.getContentType();
		objectMetadata.setContentType(mf.getContentType());
		//long size=tempFile.getSize()
		objectMetadata.setContentLength(mf.getSize());
		return objectMetadata;
		
	}

	public List<String> fromTempToProduct(List<String> tempKey) {
		
		return tempKey.stream()
				.filter(sourceKey -> !sourceKey.trim().equals("")) //필터
				.map(sourceKey->{
					String newFileName=sourceKey.substring(sourceKey.lastIndexOf("/")+1);
					String destinationKey = productPath+newFileName;
					CopyObjectRequest copyObjectRequest=new CopyObjectRequest(bucket, sourceKey, bucket, destinationKey);
					//모든유저에 읽기권한 부여
					client.copyObject(copyObjectRequest.withCannedAccessControlList(CannedAccessControlList.PublicRead));
					//client.copyObject(bucket, sourceKey, bucket, destinationKey);
					client.deleteObject(bucket, sourceKey);
					System.out.println("이동완료~!");
					return newFileName;
				})
				.collect(Collectors.toList());
			

		
		/*
		 * List<String> newNames=new ArrayList<>();
		for(String sourceKey : tempKey) {
			if(sourceKey.trim().equals(""))continue; //true일때 아래문장 실행하지말고 다음 key로 넘어가세요 
			
			String newFileName=sourceKey.substring(sourceKey.lastIndexOf("/")+1);
			String destinationKey = productPath+newFilename;
			client.copyObject(bucket, sourceKey, bucket, destinationKey);
			client.deleteObject(bucket, sourceKey);
			System.out.println("이동완료~!");
			newName.add(newFileName);
		}
		*/
	}


}
