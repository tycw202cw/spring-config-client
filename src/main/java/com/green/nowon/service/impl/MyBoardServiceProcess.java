package com.green.nowon.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.green.nowon.domain.dto.MyBoardDTO;
import com.green.nowon.mybatis.mapper.MyBoardMapper;
import com.green.nowon.service.MyBoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MyBoardServiceProcess implements MyBoardService {

	//DAO(Data Access Object): 마이바티스에서는 매퍼가 담당
	private final MyBoardMapper mapper;
	
	@Override
	public String listAll(int page,Model model) {
		int limit=5;//한페이지에 표현할 레코드(행) 개수
		int offset=limit*(page-1);
		List<MyBoardDTO> result=mapper.findAll(limit,offset);
		//log.debug(">>>>size : "+result.size());
		
		//총게시글수
		int countAll=mapper.countAll();
		//총페이지 개수
		int totalPage=countAll/limit; // 12/5
		//나머지가 있는경우 나머지를 표현할 페이지
		if(countAll%limit != 0) totalPage++;
		
		
		//System.out.println("총게시글수:"+countAll);
		model.addAttribute("list", result);
		model.addAttribute("tot", totalPage);
		return "myboard/list";
	}

	@Override
	public String saveAndRedirect(MyBoardDTO dto) {
		mapper.save(dto);
		return "redirect:my-board";
	}

	@Override
	public String readByBno(long bno, Model model, 
			HttpServletRequest request, 
			HttpServletResponse response) {
		//
		Cookie[] cookies= request.getCookies();
		Cookie old=null;
		if(cookies!=null) {
			for(Cookie cookie:cookies) {
				System.out.println(">>>>>>"+cookie.getName()+":"+cookie.getValue());
				if(cookie.getName().equals("myCookie")) old=cookie;
			}
		}
		
		
		
		//쿠키는 존재하고 다음 쿠키값(문자열)에 현재게시글 번호("[bno]") 존재하지 않으면 원래쿠키(old)에다가 추가
		if(old!=null){
			if(!old.getValue().contains("["+bno+"]")) {	
				old.setValue(old.getValue()+"_["+bno+"]");
				old.setMaxAge(30);
				response.addCookie(old); //기존쿠키에 추가
				mapper.addCount(bno); //조회수 증가
			}
		}else {//myCookie라는 이름의 쿠키가 존재하지않으면 쿠키 생성
			Cookie newCookie=new Cookie("myCookie", "["+bno+"]");
			newCookie.setMaxAge(30);
			response.addCookie(newCookie); //새로운쿠키 생성
			mapper.addCount(bno); //조회수 증가
		}
		//조회수 증가 -제한없이 무조건
		//디테일 페이지에 정보제공
		model.addAttribute("detail", mapper.findByBno(bno));
		return "myboard/detail";
	}

	@Override
	public String deleteProcess(long bno) {
		int result= mapper.deleteByBno(bno);
		log.debug(">>>삭제건수:"+result);
		return "redirect:/my-board";
	}

	@Override
	public String updateProcess(long bno, MyBoardDTO dto) throws NoSuchElementException {
		MyBoardDTO sr= mapper.findByBno(bno);
		if(sr==null) throw new NoSuchElementException("이미 삭제된 데이터 입니다.");
		
		//dto.setBno(bno);
		mapper.updateByBno(dto.bno(bno));
		
		return "redirect:/my-board/"+bno;
	}

}
