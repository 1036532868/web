package com.example.goods.mapper;

import com.example.goodsApi.domain.StockBack;
import com.example.goodsApi.domain.StockBackKey;

public interface StockBackMapper {
    int deleteByPrimaryKey(StockBackKey key);

    int insert(StockBack record);

    int insertSelective(StockBack record);

    StockBack selectByPrimaryKey(StockBackKey key);

    int updateByPrimaryKeySelective(StockBack record);

    int updateByPrimaryKey(StockBack record);
}