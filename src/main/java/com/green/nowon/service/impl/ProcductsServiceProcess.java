package com.green.nowon.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.green.nowon.aws.AwsS3BucketUtil;
import com.green.nowon.domain.dto.product.ProductListDTO;
import com.green.nowon.domain.dto.product.ProductSaveDTO;
import com.green.nowon.domain.entity.ProductEntity;
import com.green.nowon.domain.entity.ProductFileEntity;
import com.green.nowon.mybatis.mapper.ProductFileMapper;
import com.green.nowon.mybatis.mapper.ProductMapper;
import com.green.nowon.service.ProcductsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProcductsServiceProcess implements ProcductsService{

	//DAO : aws-s3
	private final AwsS3BucketUtil dao;
	private final ProductMapper mapper;//->sql -mapper.xml
	private final ProductFileMapper fileMapper;//->sql -mapper.xml
	
	@Value("${cloud.aws.s3.use-url}")
	private String imgUrl;
	
	@Override
	public Map<String, String> tempUploadProcess(MultipartFile tempFile) {
		//1.MultipartFile 데이터를 S3업로드
		//2. 업로드된 경로를 페이지(ajax의 success function의 result)로 리턴
		
		return dao.tempUpload(tempFile);
	}
	
	@Override
	public void saveProcess(ProductSaveDTO dto) {
		// 상품데이터 저장
		ProductEntity entity=dto.toProductEntity();
		mapper.save(entity);
		
		// 여러개 파일저장, temp->이동 후 업로두된 이름들 리턴됩니다.
		List<String> orgNames=dto.getImgs().stream()
					.filter(org->!org.trim().equals("")) // "" 값 제거
					.collect(Collectors.toList());
		
		List<String> newNames=dao.fromTempToProduct(dto.getTempKey());
		
		long pno=entity.getPno();//fk
		for(int i=0; i<orgNames.size(); i++) {
			
			String orgName=orgNames.get(i); //원본파일명
			String newName=newNames.get(i); //새로운이름:업로드된 이름
			Boolean defYn=false;
			if(i==0)defYn=true;
			fileMapper.save(ProductFileEntity.builder()
					.orgName(orgName).newName(newName).pno(pno).defYn(defYn)
					.build());
		}
		
	}

	@Override
	public void listProcess(Model model) {
		//1.product 2.product_file
		// List<ProductEntity> -> List<ProductListDTO>
		
		model.addAttribute("list",mapper.findByAll().stream()
				.map(ProductListDTO::new)
				.map(dto->{
					long pno=dto.getPno();
					String newName=fileMapper.findByPnoAndDefYn(pno);
					return dto.defUrl(imgUrl+newName);
					})
				.collect(Collectors.toList()));
	}
	//join쿼리 실행후 resultMap에 매핑방법
	@Override
	public void listJoinProcess(Model model) {
		System.out.println(">>>>>>>>>>>>>>>>>>>");
		mapper.findByAllJoinFile().forEach(System.out::println);
		System.out.println(">>>>>>>>>>>>>>>>>>>");
		model.addAttribute("list", mapper.findByAllJoinFile());
	}
	//join쿼리 실행후 resultMap에 매핑방법 -> DTO로 처리
	@Override
	public void listJoinProcess3(Model model) {
		model.addAttribute("list", mapper.findByAllJoinFile()
				.stream()
				.map(ProductListDTO::new)
				.collect(Collectors.toList()));
	}

}