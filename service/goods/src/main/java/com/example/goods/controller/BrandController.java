package com.example.goods.controller;

import com.example.goods.service.BrandService;
import com.example.goodsApi.domain.Brand;
import com.example.util.Result;
import com.example.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0.0
 * @Author gong_da_kai
 * @Date 2021/2/2 11:08
 * @since 1.0.0
 */
@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;
    @Autowired
    private RedisTemplate<Object, Object> redis;

    @GetMapping("/selectByCategoryId")
    @Cacheable("normal")
    public Result<List<Brand>> selectByCategoryId(Long categoryId) {

        String key = "selectByCategoryId_" + categoryId;
        List<Brand> l = (List<Brand>) redis.opsForValue().get(key);

        if (l == null) {
            l = brandService.selectByCategoryId(categoryId);
            redis.opsForValue().set(key, l);
        }

        if (l.size() == 0) {
            redis.expire(key, 60, TimeUnit.SECONDS);
            l = new ArrayList<>(0);
        }

        return new Result<>(true, StatusCode.OK, "", l);
    }

}
