package com.green.nowon.domain.dto.product;

import java.util.List;

import com.green.nowon.domain.entity.ProductEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class ProductSaveDTO {

	private String title; //tag name 하고 일치 value가 매핑
	private int price;
	private String content;
	
	public ProductEntity toProductEntity() {
		return ProductEntity.builder()
				.title(title)
				.price(price)
				.content(content)
				.build();
	}
	
	private List<String> imgs;
	private List<String> tempKey;
}
