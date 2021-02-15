package com.example.goods.service.impl;

import com.example.goods.mapper.TemplateMapper;
import com.example.goods.service.TemplateService;
import com.example.goodsApi.domain.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**
 * @version 1.0.0
 * @Author gong_da_kai
 * @Date 2021/2/2 15:33
 * @since 1.0.0
 */
@Service
@CacheConfig
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    private TemplateMapper templateMapper;


    @Override
    public Template selectById(Integer id) {
        return templateMapper.selectByPrimaryKey(id);
    }
}
