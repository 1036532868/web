package com.example.goodsApi.feign;

import com.example.goodsApi.domain.Goods;
import com.example.util.Result;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author gong_da_kai
 * @version 1.0.0
 * @date 2021/2/16 16:12
 * @since 1.0.0
 */
@FeignClient("goods-server")
@RequestMapping("/goods/spu")
public interface SpuFeign {

    /**
     * @Description TODO 新增商品, 传入json格式参数
     * @param goods spu, skuList
     * @return com.example.util.Result
     * @author gong_da_kai
     * @Date 2021/2/10 16:43
     * @since 1.0.0
     */
    @PostMapping("/add")
    Result spu(@RequestBody Goods goods);

    /**
     * @Description TODO 将id字段转为String, 防止js中因为数值过大, 产生精度不准的问题
     * @param param 查询条件的 map集合
     * @return com.example.util.Result<com.github.pagehelper.PageInfo<java.util.Map<java.lang.String,java.lang.Object>>>
     * @author gong_da_kai
     * @Date 2021/2/10 15:26
     * @since 1.0.0
     */
    @PostMapping("/pageList")
    Result<PageInfo<Map<String, Object>>> pageList(@RequestBody Map param);

    /**
     * @description TODO 可以更改一个或多个商品的审核状态
     * @param id 一个或多个商品的id
     * @param status 审核状态
     * @return com.example.util.Result message 根据
     * @author gong_da_kai
     * @date 2021/2/15 16:34
     * @since 1.0.0
     */
    @PostMapping("/audit")
    Result audit(@RequestParam("id") Long[] id, @RequestParam("status") String status);

    /**
     * @description TODO 根据 spuId 查询出spu和对应的所有sku
     * @param spuId
     * @return com.example.util.Result<com.example.goodsApi.domain.Goods>
     * @author gong_da_kai
     * @date 2021/2/22 20:54
     * @since 1.0.0
     */
    @GetMapping("/goods/{spuId}")
    Result<Goods> searchGoods(@PathVariable("spuId") Long spuId);

}
