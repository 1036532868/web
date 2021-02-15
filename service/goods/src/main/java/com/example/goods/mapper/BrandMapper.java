package com.example.goods.mapper;

import com.example.goodsApi.domain.Brand;

import java.util.List;

public interface BrandMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Brand record);

    int insertSelective(Brand record);

    Brand selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Brand record);

    int updateByPrimaryKey(Brand record);

    List<Brand> selectByCategoryId(Long id);
}