package com.example.order.mapper;

import com.example.orderApi.domain.CategoryReport;
import com.example.orderApi.domain.CategoryReportKey;

public interface CategoryReportMapper {
    int deleteByPrimaryKey(CategoryReportKey key);

    int insert(CategoryReport record);

    int insertSelective(CategoryReport record);

    CategoryReport selectByPrimaryKey(CategoryReportKey key);

    int updateByPrimaryKeySelective(CategoryReport record);

    int updateByPrimaryKey(CategoryReport record);
}