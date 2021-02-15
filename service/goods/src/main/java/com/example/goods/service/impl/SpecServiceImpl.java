package com.example.goods.service.impl;

import com.example.goods.mapper.SpecMapper;
import com.example.goods.service.SpecService;
import com.example.goodsApi.domain.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version 1.0.0
 * @Author gong_da_kai
 * @Date 2021/2/2 15:47
 * @since 1.0.0
 */
@Service
@CacheConfig
public class SpecServiceImpl implements SpecService {

    @Autowired
    private SpecMapper specMapper;

    @Override
    @Cacheable("normal")
    public List<Spec> selectByTemplateId(Integer templateId) {
        return specMapper.selectByTemplateId(templateId);
    }
}
