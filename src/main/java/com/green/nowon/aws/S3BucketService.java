package com.green.nowon.aws;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class S3BucketService {
	
	//private S3Client s3Client;
	private final AmazonS3Client client;
	
	@Value("${cloud.aws.s3.bucket}")
	private String bucket;
	
	private String temp="goods/temp/";
	
	public void upload(MultipartFile mf) {
		
		//*
		//System.out.println(">>>>AmazonS3Client:"+client);
		String contentType= mf.getContentType();
		long size=mf.getSize();
		String orgName=mf.getOriginalFilename();// 파일.이름.확장자
		System.out.println("orgName : "+ orgName);
		int idx=orgName.lastIndexOf("."); //존재하면 .위치의 인덱스번호 없으면 -1
		String newName=orgName.substring(0, idx) //파일이름만 추출 .전까지 문자열
				+"_"+(System.nanoTime()/1000000)
				+orgName.substring(idx);//확장자
		System.out.println("newName : "+ newName);
		String uuid=UUID.randomUUID().toString();
		System.out.println(">>>uuid:"+ uuid);
		
		ObjectMetadata objectMetadata=new ObjectMetadata();
		objectMetadata.setContentType(contentType);
		objectMetadata.setContentLength(size);
		
		String tempKey=temp+newName;
		
		try(InputStream is=mf.getInputStream()) {
			PutObjectRequest putObjectRequest=new PutObjectRequest(bucket, tempKey, is, objectMetadata);
			client.putObject(putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead));
			String uploadedUrl=client.getUrl(bucket, tempKey).toString();
			System.out.println(">>>>uploadedUrl : "+uploadedUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//*/
		
	}

}