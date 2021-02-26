package com.example.goodsApi.feign;

import com.example.goodsApi.domain.Sku;
import com.example.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

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

    /**
     * @description TODO 根据Id查单条
     * @param skuId
     * @return com.example.util.Result<com.example.goodsApi.domain.Sku>
     * @author gong_da_kai
     * @date 2021/2/22 20:49
     * @since 1.0.0
     */
    @GetMapping("/{skuId}")
    Result<Sku> selectById(@PathVariable("skuId") Long skuId);

    /**
     * @description TODO 商品减库存
     * @param skuId
     * @param num
     * @return com.example.util.Result
     * @author gong_da_kai
     * @date 2021/2/26 11:33
     * @since 1.0.0
     */
    @PostMapping("/sale")
    Result sale(@RequestParam("skuId") Long skuId, @RequestParam("num") Integer num);
}
