package com.example.goods.controller;

import com.example.exception.CRUDException;
import com.example.goods.service.CategoryService;
import com.example.goodsApi.domain.Category;
import com.example.util.Result;
import com.example.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0.0
 * @Author gong_da_kai
 * @Date 2021/1/31 21:26
 * @since 1.0.0
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisTemplate<Object, Object> redis;

    @GetMapping("/{id}")
    public Result<Category> selectById(@PathVariable("id") Integer id){

        String key = "category_"+ id;
        Category category = (Category) redis.opsForValue().get(key);
        if (category == null){
            category = categoryService.selectById(id);

            redis.opsForValue().set(key, category);
            if (category == null){
                redis.expire(key, 30, TimeUnit.SECONDS);
            }
        }

        return new Result<>(true, StatusCode.OK, "", category);
    }

    /**
     * @param parentIds
     * @Description TODO 返回所有 parentId 为参数数组 元素 的 Category对象
     * 先从redis中读取, 没有就调用service, 然后缓存并返回结果
     * @Return com.example.util.Result<java.util.List < com.example.goodsApi.domain.Category>>
     * @Author gong_da_kai
     * @Date 2021/2/1 8:56
     * @version 1.0.0
     * @since 1.0.0
     */
    @GetMapping("/selectByParentId")
    public Result<List<Category>> selectByParentId(Integer[] parentIds) throws CRUDException {

        if (parentIds == null || parentIds.length == 0) throw new CRUDException("没有得到任何parentId");

        //从redis中查数据
        String key = "categoryParentId_";
        for (int i : parentIds) {
            key += i;
        }

        List<Category> l = (List<Category>) redis.opsForValue().get(key);
        System.err.println("redis中的:" + l);
        if (l == null) {
            l = categoryService.selectByParentId(parentIds);
            System.err.println("数据库查出来的:" + l);
            //将数据缓存到redis
            redis.opsForValue().set(key, l);
            redis.expire(key, 30, TimeUnit.SECONDS);
        }

        return new Result<>(true, StatusCode.OK, "", l);
    }

}
