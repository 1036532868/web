package com.example.order.service.impl;

import com.example.exception.CRUDException;
import com.example.goodsApi.domain.Sku;
import com.example.goodsApi.domain.Spu;
import com.example.goodsApi.feign.SkuFeign;
import com.example.goodsApi.feign.SpuFeign;
import com.example.order.service.CartService;
import com.example.orderApi.domain.OrderItem;
import com.example.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author gong_da_kai
 * @version 1.0.0
 * @date 2021/2/25 12:00
 * @since 1.0.0
 */
@Service
@Transactional(rollbackFor = CRUDException.class)
public class CartServiceImpl implements CartService {
    @Autowired
    private RedisTemplate<Object, Object> redis;
    @Autowired
    private SkuFeign skuFeign;
    @Autowired
    private SpuFeign spuFeign;


    @Override
    public void add(Long skuId, Integer num, String username) throws CRUDException {

        OrderItem item = (OrderItem) redis.opsForHash().get("cart_"+ username, skuId);

        if (item != null){
           item.setNum(item.getNum() + num);
           item.setMoney(item.getNum() * item.getPrice());
        } else {
            Result<Sku> res =  skuFeign.selectById(skuId);
            if (!res.isFlag()) throw new CRUDException(res.getMessage());
            Sku sku = res.getData();

            item = new OrderItem();
            // 暂时将item的id作为sku 的 stringId 使用

            item.setId(sku.getId().toString());
            item.setCategoryId3(sku.getCategoryId());
            item.setSpuId(sku.getSpuId());
            item.setSkuId(sku.getId());
            item.setName(sku.getName());
            item.setPrice(sku.getPrice());
            item.setNum(num);
            item.setMoney(num * item.getPrice());
            item.setImage(sku.getImage());
            item.setIsReturn("0");

            Result<Spu> res2 = spuFeign.get(sku.getSpuId());
            Spu spu = res2.getData();
            if (spu == null) throw new CRUDException(res2.getMessage());

            item.setCategoryId1(spu.getCategory1Id());
            item.setCategoryId2(spu.getCategory2Id());
        }

        redis.opsForHash().put("cart_"+ username, skuId, item);
    }

    @Override
    public List<Object> get(String username) {
        return redis.opsForHash().values("cart_"+ username);
    }

    @Override
    public void delete(Long[] skuId, String username) throws CRUDException {
        if (redis.opsForHash().delete("cart_"+ username, skuId) != skuId.length){
            throw new CRUDException("从购物车中删除商品失败");
        }
    }

    @Override
    public void set(Long skuId, Integer num, String username) {
        OrderItem item = (OrderItem) redis.opsForHash().get("cart_"+ username, skuId);
        item.setNum(num);
        item.setMoney(item.getNum() * item.getPrice());
        redis.opsForHash().put("cart_"+ username, skuId, item);
    }
}
