package com.example.order.mapper;

import com.example.orderApi.domain.OrderConfig;

public interface OrderConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderConfig record);

    int insertSelective(OrderConfig record);

    OrderConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderConfig record);

    int updateByPrimaryKey(OrderConfig record);
}