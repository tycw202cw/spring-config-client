package com.green.nowon.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.green.nowon.domain.dto.GoodsSaveDTO;
import com.green.nowon.service.GoodsService;

import lombok.RequiredArgsConstructor;

//DispatcherServlet 가 선행해서 doGet,doPost를 해야하는걸 대신해주는 @Controller 

@RequiredArgsConstructor
@Controller
public class GoodsController {
	
	//@Autowired
	private final GoodsService service;//DI, IoC, AOP
	
	@GetMapping("/goods")
	public String list() {
		return "goods/list";
	}
	@GetMapping("/goods/new")
	public String write() {
		return "goods/write";
	}
	
	@PostMapping("/goods")
	public String save(MultipartFile img, GoodsSaveDTO dto) throws IOException, Exception {
		//System.out.println("ContentType : "+img.getContentType()); //파일타입
		//System.out.println("Name : "+img.getName()); //form input name
		//System.out.println("OriginalFilename : "+img.getOriginalFilename()); //파일이름
		//System.out.println("Size : "+img.getSize()); //byte 로 파일사이즈 리턴 long형태로
		service.saveProcess(img, dto);
		return "goods/write";
	}
}
