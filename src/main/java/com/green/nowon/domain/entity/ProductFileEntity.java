package com.green.nowon.domain.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder//빌더패턴 객체생성하기위한 표현 //Builder를 사용하려면 파라미터가있는 생성자가필수
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductFileEntity {

	private long fno;
	private String orgName;
	private String newName;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
	//fk
	private long pno;//FK Goods 테이블의 pk값만 저장
	private boolean defYn;
}
