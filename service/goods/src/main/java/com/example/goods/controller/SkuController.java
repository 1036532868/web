package com.example.goods.controller;

import com.example.exception.CRUDException;
import com.example.goods.service.SkuService;
import com.example.goodsApi.domain.Sku;
import com.example.util.Result;
import com.example.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * @param params <pre>pageNum</pre>
     *               <pre>pageSize</pre>
     *               <pre>name: 查询 品牌名、类型名、商品名  like name之一 的sku 格式"name1, name2"</pre>
     *               <pre>spec: 规格, String -> "t1:e、e、e,t2:e、e、e"</pre>
     *               <pre>brandName: 品牌名称</pre>
     *               <pre>categoryId 根据分类查询sku</pre>
     *               <pre>status sku.status 1-正常，2-下架，3-删除</pre>
     *
     *
     * @return com.example.util.Result(Map (String, Object))
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

        Map<String, Object> result = skuService.search(params);

        //System.err.println(result);

        return new Result<>(true, StatusCode.OK, "", result);
    }

    /**
    * @description TODO 根据Id查单条
    * @param skuId
    * @return com.example.util.Result<com.example.goodsApi.domain.Sku>
    * @author gong_da_kai
    * @date 2021/2/22 20:49
    * @since 1.0.0
    */
    @GetMapping("/{skuId}")
    public Result<Sku> selectById(@PathVariable("skuId") Long skuId){
        Sku sku = skuService.selectById(skuId);
        return new Result<>(true, StatusCode.OK, "", sku);
    }

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
    public Result sale(Long skuId, Integer num) throws CRUDException {
        skuService.sale(skuId, num);
        return new Result();
    }
}
