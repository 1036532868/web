package com.example.goods.service;

import com.example.exception.CRUDException;
import com.example.goodsApi.domain.Category;

import java.util.List;

/**
 * @version 1.0.0
 * @Author gong_da_kai
 * @Date 2021/1/31 21:26
 * @since 1.0.0
 */
public interface CategoryService {

    List<Category> selectByParentId(Integer[] parentIds) throws CRUDException;

    Category selectById(Integer id);
}
