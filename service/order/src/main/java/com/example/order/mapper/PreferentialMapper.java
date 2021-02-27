package com.example.order.mapper;

import com.example.orderApi.domain.Preferential;

public interface PreferentialMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Preferential record);

    int insertSelective(Preferential record);

    Preferential selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Preferential record);

    int updateByPrimaryKey(Preferential record);
}