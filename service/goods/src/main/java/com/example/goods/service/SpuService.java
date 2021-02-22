package com.example.goods.service;

import com.example.exception.CRUDException;
import com.example.goodsApi.domain.Goods;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * @version 1.0.0
 * @Author gong_da_kai
 * @Date 2021/2/4 21:02
 * @since 1.0.0
 */
public interface SpuService {
    void add(Goods goods) throws CRUDException;

    PageInfo<Map<String, Object>> pageList(Map param);

    void audit(Long[] id, String status) throws CRUDException;

    Goods goods(Long spuId);
}
