package com.example.websearch.controller;

import com.example.goodsApi.feign.SkuFeign;
import com.example.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @author gong_da_kai
 * @version 1.0.0
 * @date 2021/2/16 16:08
 * @since 1.0.0
 */
@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SkuFeign skuFeign;

    /**
    * @description TODO 调用goods服务获得数据, 然后渲染到thymeleaf返回, 可以防止百度等搜索引擎抓取页面时出现未渲染的页面
    * @param params
    * @return String
    * @author gong_da_kai
    * @date 2021/2/17 20:52
    * @since 1.0.0
    */
    @PostMapping
    public String search(@RequestBody Map<String, Object> params, Model model){

        Result<Map<String, Object>> result = skuFeign.search(params);

        if (result.isFlag()) model.addAllAttributes(result.getData());

        return "search";

    }

}
