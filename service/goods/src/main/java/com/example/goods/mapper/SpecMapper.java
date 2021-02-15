package com.example.goods.mapper;

import com.example.goodsApi.domain.Spec;

import java.util.List;

public interface SpecMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Spec record);

    int insertSelective(Spec record);

    Spec selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Spec record);

    int updateByPrimaryKey(Spec record);

    List<Spec> selectByTemplateId(Integer templateId);
}