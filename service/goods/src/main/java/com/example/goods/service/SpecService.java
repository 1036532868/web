package com.example.goods.service;

import com.example.goodsApi.domain.Spec;

import java.util.List;

/**
 * @version 1.0.0
 * @Author gong_da_kai
 * @Date 2021/2/2 15:47
 * @since 1.0.0
 */
public interface SpecService {
    List<Spec> selectByTemplateId(Integer templateId);
}
