package com.example.goods.controller;

import com.example.exception.CRUDException;
import com.example.goods.service.SpuService;
import com.example.goodsApi.domain.Goods;
import com.example.util.Result;
import com.example.util.StatusCode;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @version 1.0.0
 * @author gong_da_kai
 * @Date 2021/2/4 20:56
 * @since 1.0.0
 */
@RestController
@RequestMapping("/spu")
public class SpuController {

    @Autowired
    SpuService spuService;

    /**
    * @Description TODO 新增商品, 传入json格式参数
    * @param goods spu, skuList
    * @return com.example.util.Result
    * @author gong_da_kai
    * @Date 2021/2/10 16:43
    * @since 1.0.0
    */
    @PostMapping("/add")
    public Result spu(@RequestBody Goods goods) throws CRUDException {

        spuService.add(goods);

        return new Result();
    }

    /**
    * @Description TODO 将id字段转为String, 防止js中因为数值过大, 产生精度不准的问题
    * @param param 查询条件的 map集合
    * @return com.example.util.Result<com.github.pagehelper.PageInfo<java.util.Map<java.lang.String,java.lang.Object>>>
    * @author gong_da_kai
    * @Date 2021/2/10 15:26
    * @since 1.0.0
    */
    @PostMapping("/pageList")
    public Result<PageInfo<Map<String, Object>>> pageList(@RequestBody Map param){

        PageInfo<Map<String, Object>> info = spuService.pageList(param);

        return new Result<>(true, StatusCode.OK, "", info);
    }

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
    public Result audit(Long[] id, String status) throws CRUDException {
        spuService.audit(id, status);
        return new Result(true, StatusCode.OK, "1".equals(status) ? "审核通过" : "审核不通过");
    }

    @GetMapping("/goods/{spuId}")
    public Result<Goods> searchGoods(@PathVariable("spuId") Long spuId){

        Goods goods = spuService.goods(spuId);

        return new Result<>(true, StatusCode.OK, "", goods);
    }
}
