package com.example.goods.service;

import com.example.goodsApi.domain.Brand;

import java.util.List;

/**
 * @version 1.0.0
 * @Author gong_da_kai
 * @Date 2021/2/2 11:15
 * @since 1.0.0
 */
public interface BrandService {
    List<Brand> selectByCategoryId(Long categoryId);
}
