package com.green.nowon.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardUpdateDTO {
	private long bno;
	private String title;
	private String content;
}
