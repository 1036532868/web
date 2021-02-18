package com.example.goods.mapper;

import com.example.goodsApi.domain.Category;

import java.util.List;

public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    List<Category> selectByParentId(Integer i);

    List<Category> selectByParentIds(Integer[] parentIds);

    List<Category> selectByIds(List<Integer> categoryIds);
}

