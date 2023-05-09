package com.green.nowon.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.green.nowon.domain.entity.GoodsEntity;

@Mapper
public interface GoodsMapper {

	void save(GoodsEntity entity);

	List<GoodsEntity> findAll();

}
