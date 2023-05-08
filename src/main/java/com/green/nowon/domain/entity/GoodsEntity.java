package com.green.nowon.domain.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor//필드6개 모두를 초기화역할을 하는 파라미터가 정의된 생성자
@NoArgsConstructor//인자없이 생성할수 있는 생성자//파라미터가 정의되지않은 생성자
@Getter
public class GoodsEntity {
	
	private long gno;
	private String name;
	private String content;
	private int price;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
	
}
