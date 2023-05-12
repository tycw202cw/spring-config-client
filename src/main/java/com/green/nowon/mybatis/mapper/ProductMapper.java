package com.green.nowon.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.green.nowon.domain.dto.product.ProductSaveDTO;
import com.green.nowon.domain.entity.ProductEntity;

@Mapper
public interface ProductMapper {

	void save(ProductEntity productEntity);

	List<ProductEntity> findByAll();

	List<ProductEntity> findByAllJoinFile();


}
