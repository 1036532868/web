package com.example.goods.controller;

import com.example.goods.service.SpecService;
import com.example.goodsApi.domain.Spec;
import com.example.util.Result;
import com.example.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0.0
 * @Author gong_da_kai
 * @Date 2021/2/2 15:37
 * @since 1.0.0
 */
@RestController
@RequestMapping("/spec")
public class SpecController {

    @Autowired
    private RedisTemplate<Object, Object> redis;
    @Autowired
    private SpecService specService;

    /**
    * @Description TODO 根据 templateId 查询所有关联的 spec
    * @param templateId
    * @Return com.example.util.Result<java.util.List<com.example.goodsApi.domain.Spec>>
    * @Author gong_da_kai
    * @Date 2021/2/2 15:46
    * @version 1.0.0
    * @since 1.0.0
    */
    @GetMapping("/selectByTemplateId")
    public Result<List<Spec>> selectByTemplateId(Integer templateId) {

        String key = "spec_template_" + templateId;
        List<Spec> l = (List<Spec>) redis.opsForValue().get(key);

        if (l == null){
            l = specService.selectByTemplateId(templateId);
            redis.opsForValue().set(key, l);
            redis.expire(key, 30, TimeUnit.SECONDS);
        }

        return new Result<>(true, StatusCode.OK, "", l);

    }

}
