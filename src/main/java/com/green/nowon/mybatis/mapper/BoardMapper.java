package com.green.nowon.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.ui.Model;

import com.green.nowon.domain.dto.BoardDTO;
import com.green.nowon.domain.dto.BoardSaveDTO;
import com.green.nowon.domain.dto.BoardUpdateDTO;

@Mapper //spring mybatis에서 db에 CRUD(DML) 하기위한 객체
public interface BoardMapper {
	//xml파일을 이용하는 경우 ㅡ mapper.xml 에서 sql 처리
	
	void save(BoardSaveDTO dto);

	List<BoardDTO> findAll();

	BoardDTO findByBno(long bno);

	void deleteByBno(long bno);

	void updateByBto(BoardUpdateDTO dto);
	
}
