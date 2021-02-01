package com.example.goods.mapper;

import com.example.goodsApi.domain.Pref;

public interface PrefMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Pref record);

    int insertSelective(Pref record);

    Pref selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Pref record);

    int updateByPrimaryKey(Pref record);
}