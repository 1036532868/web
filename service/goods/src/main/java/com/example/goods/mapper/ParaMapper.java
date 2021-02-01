package com.example.goods.mapper;

import com.example.goodsApi.domain.Para;

public interface ParaMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Para record);

    int insertSelective(Para record);

    Para selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Para record);

    int updateByPrimaryKey(Para record);
}