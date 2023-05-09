package com.green.nowon.domain.dto.goods;

import com.green.nowon.domain.entity.GoodsEntity;

import lombok.Setter;
import lombok.ToString;

@Setter
public class GoodsSaveDTO {
	private String name;
	private int price;
	private String content;
	
	//편의 메서드를 만들게요
	public GoodsEntity toEntity() {
		return GoodsEntity.builder()//innerClass 인 GoodsEntityBuilder 객체 생성
				.name(name).price(price).content(content)//set name,price,content 한 GoodsEntityBuilder 객체
				.build();//GoodsEntity 생성
		//return new GoodsEntity();
		
	}
}
