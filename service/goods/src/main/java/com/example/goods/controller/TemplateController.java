package com.example.goods.controller;

import com.example.goods.service.TemplateService;
import com.example.goodsApi.domain.Template;
import com.example.util.Result;
import com.example.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @version 1.0.0
 * @Author gong_da_kai
 * @Date 2021/2/2 15:23
 * @since 1.0.0
 */
@RestController
@RequestMapping("/template")
public class TemplateController {

    @Autowired
    private RedisTemplate<Object, Object> redis;
    @Autowired
    private TemplateService templateService;

    /**
    * @Description TODO 根据主键id查询
    * @param id
    * @Return com.example.util.Result<com.example.goodsApi.domain.Template>
    * @Author gong_da_kai
    * @Date 2021/2/2 15:26
    * @version 1.0.0
    * @since 1.0.0
    */
    @GetMapping("/selectById")
    public Result<Template> selectById(Integer id){

        String key = "template_" + id;
        Template t = (Template) redis.opsForValue().get(key);
        if (t == null){
            t = templateService.selectById(id);
            redis.opsForValue().set(key, t);
            redis.expire(key, 20, TimeUnit.SECONDS);
        }


        return new Result<>(true, StatusCode.OK, "", t);

    }

}
