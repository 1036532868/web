package com.example.goods.service;

import com.example.goodsApi.domain.Template;

/**
 * @version 1.0.0
 * @Author gong_da_kai
 * @Date 2021/2/2 15:32
 * @since 1.0.0
 */
public interface TemplateService {
    Template selectById(Integer id);
}
