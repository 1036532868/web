package com.example.goods.mapper;

import com.example.goodsApi.domain.Sku;

import java.util.List;
import java.util.Map;

public interface SkuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Sku record);

    int insertSelective(Sku record);

    Sku selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Sku record);

    int updateByPrimaryKey(Sku record);

    List<Sku> search(Map<String, Object> params);

    List<Sku> selectBySpuId(Long spuId);

    int sale(Long skuId, Integer num);
}