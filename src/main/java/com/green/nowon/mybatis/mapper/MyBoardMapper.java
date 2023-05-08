package com.green.nowon.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.green.nowon.domain.dto.MyBoardDTO;

@Mapper
public interface MyBoardMapper {
	//쿼리의 결과값을 담는 DTO
	List<MyBoardDTO> findAll(@Param("limit") int limit,@Param("offset") int offset);

	int save(MyBoardDTO dto);

	MyBoardDTO findByBno(long bno);//where절에 pk컬럼(bno) 값이 일치하는경우이므로 단일행결과

	int deleteByBno(long bno);

	int updateByBno(MyBoardDTO bno);

	void addCount(long bno);

	@Select("select count(*) from my_board")
	int countAll();

	
	


}
