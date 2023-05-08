package com.green.nowon.service.impl;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.green.nowon.domain.dto.GoodsSaveDTO;
import com.green.nowon.domain.entity.GoodsEntity;
import com.green.nowon.mybatis.mapper.GoodsFileMapper;
import com.green.nowon.mybatis.mapper.GoodsMapper;
import com.green.nowon.service.GoodsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class GoodsServiceProcess implements GoodsService {

	//DAO : mapper, repositroy --> interface
	private final GoodsMapper mapper; //DI 외부에서 주입: 내부코드에서 결정하지 않아요(IoC)
	
	private final GoodsFileMapper fileMapper;
	
	@Override
	public void saveProcess(MultipartFile img, GoodsSaveDTO dto) throws Exception, IOException {
		//상품정보 저장 DB goods 테이블에 저장
		//mapper.save(dto.toEntity());
		//파일정보 저장 DB goods_file 테이블에 저장
		long size=img.getSize();
		String name=img.getOriginalFilename();
		String url="E:/ncs2023/spring/spring-config-client/bin/main/static/images/upload/goods/"; //업로드되는 서버의주소
		File dest=new File(url+name);
		img.transferTo(dest);
		System.out.println(">>>>>:파일업로드완료!");
		//서버에 업로드
		
		
	}

}
