package com.green.nowon.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.green.nowon.domain.dto.MyBoardDTO;
import com.green.nowon.service.MyBoardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor// final 필드를 파라미터변수로 사용하여 만들어지는 생성자
@Controller
public class MyBoardController {
	
	private final MyBoardService service; //생성자DI - RAC를 통해 DI / 필드 DI는 오토와이어드만해도됨
	
	@GetMapping("/my-board")//return "myboard/list";//templates경로
	public String pageAndList(
			//url ? page=1 / null일때 parseInt를 할수없기때문에 default밸류를 지정해준다
			@RequestParam(defaultValue = "1") int page,
			Model model) {
		System.out.println(page);
		return service.listAll(page,model);	
	}

	//글쓰기 페이지 이동
	@GetMapping("/my-board/new")
	public String writePage() {
		return "/myboard/write";
	}
	@PostMapping("/my-board")
	public String saveAndList(MyBoardDTO dto) {
		return service.saveAndRedirect(dto);
	}
	
	//@RequestMapping(method = RequestMethod.GET, value = "/my-board/{bno}")
	@GetMapping("/my-board/{bno}")
	public String detailPageAndData(
			@PathVariable long bno, 
			Model model,
			HttpServletRequest request,
			HttpServletResponse response ) {
		return service.readByBno(bno,model,request,response);
	}

	//@RequestMapping(method = RequestMethod.DELETE, value = "/my-board/{bno}")
	@DeleteMapping("/my-board/{bno}")
	public String delete(@PathVariable long bno) {
		return service.deleteProcess(bno);
	}
	//@RequestMapping(method = RequestMethod.PUT, value = "/my-board/{bno}")
	@PutMapping("/my-board/{bno}")
	public String update(@PathVariable long bno, MyBoardDTO dto) {
		return service.updateProcess(bno,dto);
	}

}
