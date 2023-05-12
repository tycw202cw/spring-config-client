package com.green.nowon.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.green.nowon.domain.dto.product.ProductSaveDTO;
import com.green.nowon.service.ProcductsService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ProcductsController {
	
	private final ProcductsService service;
	
	@PostMapping("/products")
	public String save(ProductSaveDTO dto) {
		service.saveProcess(dto);
		return "redirect:/products";
	}
	
	@GetMapping("/products")
	public String list(Model model) {
		service.listProcess(model);
		return "product/list";
		//service.listJoinProcess(model);
		//return "product/list2";
		//service.listJoinProcess3(model);
		//return "product/list";
	}
	
	@GetMapping("/products/new")
	public String writePage() {
		return "product/write";
	}
	
	//비동기 요청처리입니다.
	@ResponseBody //성공시 success의 함수에 파라미터로전달 리턴타입이 응답테이터에요 JSON으로 보내요->
	@PostMapping("/products/temp-upload")
	public Map<String,String> tempUpload(MultipartFile tempFile, String tempKey) {
		System.out.println(">>>tempKey:"+tempKey);
		//업로드된 s3의 경로, 파일이름등
		return service.tempUploadProcess(tempFile);
		
	}
	

}