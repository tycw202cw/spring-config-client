package com.green.nowon.domain.dto.goods;

import java.time.LocalDateTime;
import java.util.List;

import com.green.nowon.domain.entity.GoodsEntity;
import com.green.nowon.domain.entity.GoodsFileEntity;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class GoodsListDTO {

	private long gno;
	private String name;
	//private String content;
	private int price;
	//private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
	
	//상품정보의 이미지 
	List<GoodsFileEntity> imgs;
	//편의 메서드 
	public GoodsListDTO imgs(List<GoodsFileEntity> imgs) {
		this.imgs=imgs;
		return this;
	}
	
	public GoodsListDTO(GoodsEntity e) {
		this.gno = e.getGno();
		this.name = e.getName();
		this.price = e.getPrice();
		this.updatedDate = e.getUpdatedDate();
	}
	
	
}
