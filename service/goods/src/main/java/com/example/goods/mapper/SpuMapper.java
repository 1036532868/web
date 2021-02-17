package com.example.goods.mapper;

import com.example.goodsApi.domain.Spu;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SpuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Spu record);

    int insertSelective(Spu record);

    Spu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Spu record);

    int updateByPrimaryKeyWithBLOBs(Spu record);

    int updateByPrimaryKey(Spu record);

    List<Map<String, Object>> selectSelective(Map param);

    List<Spu> selectByIds(Set<Long> spuIds);
}