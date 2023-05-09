package com.green.nowon.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.green.nowon.aws.S3BucketService;
import com.green.nowon.domain.dto.goods.GoodsListDTO;
import com.green.nowon.domain.dto.goods.GoodsSaveDTO;
import com.green.nowon.domain.entity.GoodsEntity;
import com.green.nowon.domain.entity.GoodsFileEntity;
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
	
	private final S3BucketService service;
	
	@Override
	public void saveProcess(MultipartFile img, GoodsSaveDTO dto) throws Exception, IOException {
		service.upload(img);
		//상품정보 저장 DB goods 테이블에 저장
		GoodsEntity goods=dto.toEntity();
		System.out.println("inster 실행전:"+goods);
		mapper.save(goods);
		System.out.println("inster 실행후:"+goods);//pk컬럼값이 매핑된결과를 볼 수 있다.
		
		  //서버에 업로드 long size=img.getSize(); String name=img.getOriginalFilename();
		  long size=img.getSize();
		  String url="/images/upload/goods/";
		  String name=img.getOriginalFilename();
		  //String fileFolder= "E:/ncs2023/spring/spring-config-client/bin/main/static/images/upload/goods/";
		  //File dest=new File(fileFolder+name); 물리경로에 반영되는것
		  //"classpath:"
		  ClassPathResource cpr=new ClassPathResource("/static"+url);
		  //tomcat이 돌아가는 위치 바이너리 경로에 즉각 반영되는곳
		  System.out.println("업로드 폴더:"+cpr.getPath().toString());
		  File dest=new File(cpr.getFile(),name);
		  img.transferTo(dest);
		  //업로드되는 서버의주소 File dest=new File(url+name); img.transferTo(dest);
		  
		  //파일정보 저장 DB goods_file 테이블에 저장
		  fileMapper.save(GoodsFileEntity.builder()		  
		  .size(size).url(url).name(name).gno(goods.getGno())
		  .build());
		
		
	}

	@Override
	public void getlistProcess(Model model) {
		//DB에서 상품정보 읽어오고 파일도 있으면 같이 읽어와야한다.
		List<GoodsListDTO> list =mapper.findAll()
				.stream()
				.map(GoodsListDTO::new)
				//상품의 이미지
				.map(t-> t.imgs(fileMapper.findByGno(t.getGno())))
				.collect(Collectors.toList());
		
		//상품테이블의 pk가 필요
		for(GoodsListDTO t : list) {
			long gno=t.getGno();
			
			//상품의 이미지
			List<GoodsFileEntity> result=fileMapper.findByGno(gno);
			t.imgs(result);
			System.out.println(t);
		}
		
		//list.forEach(System.out::println);
		//list.forEach(t->{System.out.println("++++++"+t);});
		//list.forEach((t)->{System.out.println("++++++"+t);});
		/*
		 * list.forEach( new Consumer<GoodsListDTO>() {
		 * 
		 * @Override public void accept(GoodsListDTO t) {
		 * System.out.println("******"+t); } } );
		 */
		model.addAttribute("list",list);
		
		//Stream<GoodsEntity>
		/*List<GoodsListDTO> list = mapper.findAll() //List<GoodsEntity>
				.stream() //Stream<GoodsEntity>
				.map(GoodsListDTO::new)
				.collect(Collectors.toList());*/
				//.map((e)->new GoodsListDTO(e))
				/* 람다식(Lamba) : 추상메서드가 하나만 존재하는 인터페이스 중 Function ()->{}  
				* new Function<GoodsEntity, GoodsListDTO>() {
				* @Override public GoodsListDTO apply(GoodsEntity t) { return new
				* GoodsListDTO(t); } }
				 */
				 
		//List<GoodsListDTO> list = mapper.findAll().stream().map(GoodsListDTO::new).collect(Collectors.toList()); 
		
		/*
		 * List<GoodsEntity> result=mapper.findAll();
		 * List<GoodsListDTO> list=new ArrayList<>();
		 * for(GoodsEntity ent:result) { GoodsListDTO dto=new
		 * GoodsListDTO(ent); list.add(dto); }
		 */
	}

}
