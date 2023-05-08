package com.green.nowon.service.impl;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.green.nowon.domain.dto.BoardDTO;
import com.green.nowon.domain.dto.BoardSaveDTO;
import com.green.nowon.domain.dto.BoardUpdateDTO;
import com.green.nowon.mybatis.mapper.BoardMapper;
import com.green.nowon.service.BoardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardServiceProcess implements BoardService {

	private final BoardMapper mapper;
	
	@Override
	public void save(BoardSaveDTO dto) {
		
		mapper.save(dto);
		
	}

	@Override
	public void findAll(Model model) {
		List<BoardDTO> result= mapper.findAll();
		model.addAttribute("list", result);

		//model.addAttribute("today", LocalDate.now());
	}

	@Override
	public void detail(long bno, Model model) {
		// 1. DB에서 상세정보 읽어와요(매퍼를 통해 쿼리를 실행) - where절에 bno컬럼값이 bno 변수값이랑 같은
		//java8+에서는 Optional로 예외처리가능
		BoardDTO result=mapper.findByBno(bno);
		// 2. 읽어온 정보를 Model에 담으면 페이지에서 확인가능
		model.addAttribute("detail", result);
		
	}

	@Override
	public void delete(long bno) {
		mapper.deleteByBno(bno);
	}

	@Override
	public void update(BoardUpdateDTO dto) {
		mapper.updateByBto(dto);
	}

}
