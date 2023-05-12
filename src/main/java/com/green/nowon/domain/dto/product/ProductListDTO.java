package com.green.nowon.domain.dto.product;

import java.time.LocalDateTime;

import com.green.nowon.domain.dto.goods.GoodsListDTO;
import com.green.nowon.domain.entity.ProductEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter //thymeleaf에서 데이터를 추출하려면 getter메서드가 필요합니다.
public class ProductListDTO {
	
	private long pno;
	private String title;
	private int price;
	private LocalDateTime updatedDate;
	
	//list페이지에서 추가로 def이미지 1장
	private String defUrl;
	//default 세팅해주는 편의메서드
	public ProductListDTO defUrl(String defUrl) {
		this.defUrl=defUrl;
		return this;
	}
	//Entity 클래스에서 일부만 추출해서 생성
	public ProductListDTO(ProductEntity e) {
		this.pno = e.getPno();
		this.title = e.getTitle();
		this.price = e.getPrice();
		this.updatedDate = e.getUpdatedDate();
		
		if(e.getFiles()!=null) {
		String path = "//s3.ap-northeast-2.amazonaws.com/myweb.fileupload.bukett/product/images/";
		defUrl = path + e.getFiles().get(0).getNewName();
		}
	}
	
	
}
