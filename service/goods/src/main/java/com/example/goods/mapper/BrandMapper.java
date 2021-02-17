package com.example.goods.mapper;

import com.example.goodsApi.domain.Brand;

import java.util.List;
import java.util.Set;

public interface BrandMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Brand record);

    int insertSelective(Brand record);

    Brand selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Brand record);

    int updateByPrimaryKey(Brand record);

    List<Brand> selectByCategoryId(Long id);

    List<Brand> selectByIds(Set<Integer> brandIds);
}