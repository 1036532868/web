package com.example.goodsApi.feign;

import com.example.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @author gong_da_kai
 * @version 1.0.0
 * @date 2021/2/16 17:05
 * @since 1.0.0
 */
@FeignClient("goods-server")
@RequestMapping("/goods/sku")
public interface SkuFeign {

    /**
     * @param params <pre>name: 查询 品牌名、类型名、规格、商品名  like name 的sku</pre>
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
    Result<Map<String, Object>> search(@RequestBody Map<String, Object> params);

}