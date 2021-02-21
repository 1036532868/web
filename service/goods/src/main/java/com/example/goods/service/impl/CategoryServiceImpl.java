package com.example.goods.service.impl;

import com.example.exception.CRUDException;
import com.example.goods.mapper.CategoryMapper;
import com.example.goods.service.CategoryService;
import com.example.goodsApi.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version 1.0.0
 * @Author gong_da_kai
 * @Date 2021/1/31 21:27
 * @since 1.0.0
 */
@Service
@CacheConfig
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private RedisTemplate<Object, Object> redis;

    /**
     * @param parentIds
     * @Description TODO 返回所有 parentId 为参数数组 元素 的 Category对象
     * 从数据库中查询 CategoryList, 并缓存到ehcache
     * @Return java.util.List<com.example.goodsApi.domain.Category>
     * @Author gong_da_kai
     * @Date 2021/2/1 9:08
     * @version 1.0.0
     * @since 1.0.0
     */
    @Override
    @Cacheable("normal")
    public List<Category> selectByParentId(Integer[] parentIds) throws CRUDException {

        /*if (parentIds == null || parentIds.length == 0) throw new CRUDException("没有得到任何parentId");

        //从redis中查数据
        StringBuffer key = new StringBuffer(30);
        key.append("category_");
        for (int i : parentIds) {
            key.append(i);
        }

        List<Category> l = (List<Category>) redis.opsForValue().get(key.toString());

        if (l == null){
            l = getCategoryList(parentIds, l);

            //将数据缓存到redis
            redis.opsForValue().set(key.toString(), l);
        }

        if (l == null){
            // 如果没有结果, 返回一个空集合, 设置redis数据的生命
            l = new ArrayList<>(0);

            redis.expire(key.toString(), 60, TimeUnit.SECONDS);
        }*/
        List<Category> l = null;

        int len = parentIds.length;
        if (len == 1) {
            l = categoryMapper.selectByParentId(parentIds[0]);
        }

        if (len > 1) {
            l = categoryMapper.selectByParentIds(parentIds);

        }
        return l;
    }

    @Override
    public Category selectById(Integer id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

}
