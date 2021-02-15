package com.example.goods.service.impl;

import com.example.goods.mapper.BrandMapper;
import com.example.goods.service.BrandService;
import com.example.goodsApi.domain.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version 1.0.0
 * @Author gong_da_kai
 * @Date 2021/2/2 11:15
 * @since 1.0.0
 */
@Service
@CacheConfig
public class BrandServiceImpl implements BrandService {

    @Autowired
    private RedisTemplate<Object, Object> redis;
    @Autowired
    private BrandMapper brandMapper;


    @Override
    @Cacheable("normal")
    public List<Brand> selectByCategoryId(Long categoryId) {

        return brandMapper.selectByCategoryId(categoryId);

    }
}
