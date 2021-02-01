package com.example.goods.Service.impl;

import com.example.exception.CRUDException;
import com.example.goods.Service.CategoryService;
import com.example.goods.mapper.CategoryMapper;
import com.example.goodsApi.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0.0
 * @Author gong_da_kai
 * @Date 2021/1/31 21:27
 * @since 1.0.0
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * @param parentIds
     * @Description TODO 返回所有 parentId 为参数数组 元素 的 Category对象
     * @Return java.util.List<com.example.goodsApi.domain.Category>
     * @Author gong_da_kai
     * @Date 2021/2/1 9:08
     * @version 1.0.0
     * @since 1.0.0
     */
    @Override
    public List<Category> selectByParentId(int[] parentIds) throws CRUDException {

        if (parentIds == null) throw new CRUDException("没有得到任何parentId");

        //此处为了即使没有查出任何结果, 也能返回一个空集合, 而不是null
        List<Category> l = new ArrayList<>();

        int len = parentIds.length;
        if (len == 1) {
            l = categoryMapper.selectByParentId(parentIds[0]);
        }

        if (len > 1) {
            l = categoryMapper.selectByParentIds(parentIds);

        }
        return l;
    }
}
