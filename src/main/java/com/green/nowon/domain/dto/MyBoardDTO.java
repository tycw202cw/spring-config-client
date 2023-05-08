package com.green.nowon.domain.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter//타임리프에서 EL표현식을쓸때 사용
@Setter
public class MyBoardDTO {

	private long bno; 
	private String title;
	private String content;
	private String writer;
	private int readCount;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
	
	//편의메서드
	public MyBoardDTO bno(long bno) {
		this.bno=bno;
		return this;//현재세팅된 자기자신 MyBoardDTO
	}
}
