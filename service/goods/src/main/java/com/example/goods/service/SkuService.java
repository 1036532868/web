package com.example.goods.service;

import com.example.goodsApi.domain.Sku;

import java.util.Map;

/**
 * @author gong_da_kai
 * @version 1.0.0
 * @date 2021/2/16 16:41
 * @since 1.0.0
 */
public interface SkuService {
    Map<String, Object> search(Map<String, Object> params);

    Sku selectById(Long skuId);
}
