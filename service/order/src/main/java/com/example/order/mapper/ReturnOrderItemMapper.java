package com.example.order.mapper;

import com.example.orderApi.domain.ReturnOrderItem;

public interface ReturnOrderItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ReturnOrderItem record);

    int insertSelective(ReturnOrderItem record);

    ReturnOrderItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ReturnOrderItem record);

    int updateByPrimaryKey(ReturnOrderItem record);
}