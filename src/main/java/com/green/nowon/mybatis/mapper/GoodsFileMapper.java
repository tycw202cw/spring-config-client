package com.green.nowon.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.green.nowon.domain.entity.GoodsFileEntity;

@Mapper
public interface GoodsFileMapper {
	
	void save(GoodsFileEntity goodsFileEntity);

	List<GoodsFileEntity> findByGno(long gno);

}
