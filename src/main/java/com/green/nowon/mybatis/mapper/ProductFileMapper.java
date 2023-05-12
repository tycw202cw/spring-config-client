package com.green.nowon.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.green.nowon.domain.entity.ProductFileEntity;

@Mapper
public interface ProductFileMapper {

	void save(ProductFileEntity build);

	String findByPnoAndDefYn(long pno);

}
