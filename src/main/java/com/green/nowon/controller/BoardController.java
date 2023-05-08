package com.green.nowon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import com.green.nowon.domain.dto.BoardSaveDTO;
import com.green.nowon.domain.dto.BoardUpdateDTO;
import com.green.nowon.service.BoardService;
import com.green.nowon.service.impl.BoardServiceProcess;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class BoardController {

	private final BoardService service;
	
	//게시글페이지로 이동하면서 db에서 모든데이터 가져와서 표현
	@GetMapping("/board")
	public String board(Model model) {//데이터를 갖고가려면 페이지까지 이동시켜주는 객체
		service.findAll(model);
		return "/board/list";//list.html로 이동
	}
	
	@GetMapping("/board/new")
	public String write() {
		return "/board/write";//
	}
	
	//@GetMapping("/board") 동일한 url이나 method가 다르면 허용
	@PostMapping("/board")
	public String write(BoardSaveDTO dto) {//파라미터 매핑
		//JPS 에서String title=request.getParameter("title"); DTO로 처리
		log.debug(">>>>>>:"+dto.toString());
		service.save(dto);
		return "redirect:/board";
	}
	
	//상세페이지 매핑
	//@GetMapping({"/board/1","/board/2","/board/3"})
	@GetMapping("/board/{no}")
	public String detail(@PathVariable(name = "no") long bno, Model model) {
		//@PathVariable(name = "no") //url의 변수를 값을 매핑할때 사용
		//path변수와 파라미터 변수이름을 다르게 쓸경우 name속성을 지정해야함 같으면 생략가능
		//Model model 객체 : 페이지에 데이터 전달이 필요한경우 파라미터 변수로 선언
		log.debug(">>>>>>:"+bno);
		service.detail(bno, model);
		return "/board/detail";
	}
	
	//method="post" , _method="DELETE"
	//삭제하기위한 요청메서드 일때 @DeleteMapping 사용가능
	@DeleteMapping("/board/{no}")
	public String delete(@PathVariable(name = "no") long bno) {//삭제할 pk값이 no -> bno
		
		log.debug(">>>>>> DeleteMapping 삭제 :"+bno);
		
		service.delete(bno);
		
		return "redirect:/board";
		//redirect: ->웹페이지의 url 변경 요청 /board
	}
	
	//수정작업하기위한 매핑메서드
	@PutMapping("/board/{bno}")
	public String update(@PathVariable long bno, BoardUpdateDTO dto) {
		
		service.update(dto);
		
		return "redirect:/board/"+bno;
		//return "redirect:/board/"+dto.getBno();
		
	}
	
}
