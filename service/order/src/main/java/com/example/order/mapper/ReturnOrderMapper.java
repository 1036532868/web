package com.example.order.mapper;

import com.example.orderApi.domain.ReturnOrder;

public interface ReturnOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ReturnOrder record);

    int insertSelective(ReturnOrder record);

    ReturnOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ReturnOrder record);

    int updateByPrimaryKey(ReturnOrder record);
}