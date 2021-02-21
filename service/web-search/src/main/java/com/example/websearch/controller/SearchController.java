package com.example.websearch.controller;

import com.example.goodsApi.domain.Brand;
import com.example.goodsApi.feign.SkuFeign;
import com.example.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
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
    * @param pageNum pageSize 固定为60
    *        name  搜索栏输入的内容
    *        spec  选中的规格, "name1:s1","name2:s1、s2",...
    *        categoryId "id,name"
    * @return String
    * @author gong_da_kai
    * @date 2021/2/17 20:52
    * @since 1.0.0
    */
    @GetMapping
    public String search(Integer pageNum, String name, String spec, String categoryId,  Model model){
        String[] nameArray = name.split("\\s");
        StringBuilder newName = new StringBuilder();
        for (int i = 0; i < nameArray.length; i++) {
            newName.append(nameArray[i]);
            if (i < nameArray.length - 1){
                newName.append(",");
            }
        }

        Map<String, Object> params = new HashMap<>();
        params.put("pageNum", pageNum);
        params.put("pageSize", 60);
        params.put("name", newName.toString());
        params.put("spec", spec);
        if (categoryId != null && !"".equals(categoryId)) {
            params.put("categoryId", Integer.valueOf(categoryId.split(",")[0]));
        }
        params.put("status", "1");

        Result<Map<String, Object>> result = skuFeign.search(params);

        Map<String, Object> resultMap = result.getData();

        List<Brand> brandList = (List<Brand>) resultMap.get("brandList");
        if (brandList == null || brandList.size() < 1){
            resultMap.put("brandList", null);
        }

        if (result.isFlag()) model.addAllAttributes(resultMap);

        // 将 param 中的某些参数还原, 在页面上使用
        params.put("categoryId", categoryId);
        params.put("name", name);
        model.addAllAttributes(params);
        return "search";

    }

}
