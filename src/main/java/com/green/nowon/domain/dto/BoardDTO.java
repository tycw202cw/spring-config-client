package com.green.nowon.domain.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class BoardDTO {
	private long bno;
	private String title;
	private String content;
	private int readCount; //read_count
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
}
