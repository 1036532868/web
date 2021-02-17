package com.example.goods.service;

import java.util.Map;

/**
 * @author gong_da_kai
 * @version 1.0.0
 * @date 2021/2/16 16:41
 * @since 1.0.0
 */
public interface SkuService {
    Map<String, Object> searchByName(Map<String, Object> params);

    Map<String, Object> searchByCategoryId(Map<String, Object> params);
}
