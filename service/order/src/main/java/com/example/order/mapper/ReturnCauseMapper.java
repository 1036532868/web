package com.example.order.mapper;

import com.example.orderApi.domain.ReturnCause;

public interface ReturnCauseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ReturnCause record);

    int insertSelective(ReturnCause record);

    ReturnCause selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ReturnCause record);

    int updateByPrimaryKey(ReturnCause record);
}