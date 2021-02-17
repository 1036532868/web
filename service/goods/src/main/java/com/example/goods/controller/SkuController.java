package com.example.goods.controller;

import com.example.goods.service.SkuService;
import com.example.util.Result;
import com.example.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author gong_da_kai
 * @version 1.0.0
 * @date 2021/2/16 16:34
 * @since 1.0.0
 */
@RestController
@RequestMapping("/sku")
public class SkuController {

    @Autowired
    private SkuService skuService;

    /**
     * @param params <pre>name: 查询 品牌名、类型名、商品名  like name 的sku</pre>
     *               <pre>spec: 规格, String -> "spec1, spec2, ..."</pre>
     *               <pre>categoryId 根据分类查询sku, name 和 categoryId 不能共存</pre>
     *               <pre>status sku.status 1-正常，2-下架，3-删除</pre>
     *               <pre>pageNum</pre>
     *               <pre>pageSize</pre>
     *
     * @return com.example.util.Result<java.util.Map < java.lang.String, java.lang.Object>>
     * <pre>"pageInfo" : PageInfo(Sku)</pre>
     * <pre>"brandList" : List(Brand)</pre>
     * <pre>"categoryMap"/"specList" : 分类Map(String, List(Category)) 或 specList(Spec), 不会共存</pre>
     *
     * @description TODO
     * @author gong_da_kai
     * @date 2021/2/17 20:19
     * @since 1.0.0
     */
    @PostMapping("/search")
    public Result<Map<String, Object>> search(@RequestBody Map<String, Object> params) {

        String name = (String)params.get("name");
        Map<String, Object> result = null;
        if (name != null && !"".equals(name)) result = skuService.searchByName(params);

        Integer categoryId = (Integer) params.get("categoryId");
        if (categoryId != null) result = skuService.searchByCategoryId(params);

        return new Result<>(true, StatusCode.OK, "", result);
    }
}
