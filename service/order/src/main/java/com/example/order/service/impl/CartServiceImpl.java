package com.example.order.service.impl;

import com.example.exception.CRUDException;
import com.example.goodsApi.domain.Sku;
import com.example.goodsApi.feign.SkuFeign;
import com.example.order.service.CartService;
import com.example.orderApi.domain.OrderItem;
import com.example.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gong_da_kai
 * @version 1.0.0
 * @date 2021/2/25 12:00
 * @since 1.0.0
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private RedisTemplate<Object, Object> redis;
    @Autowired
    private SkuFeign skuFeign;

    @Override
    public void add(Long skuId, Integer num, String username) throws CRUDException {


        OrderItem item = (OrderItem) redis.opsForHash().get("cart_"+ username, skuId);

        if (item != null){
           item.setNum(item.getNum() + num);
        } else {
            Result<Sku> res =  skuFeign.selectById(skuId);
            if (!res.isFlag()) throw new CRUDException(res.getMessage());
            Sku sku = res.getData();

            item = new OrderItem();
            item.setCategoryId3(sku.getCategoryId());
            item.setSpuId(sku.getSpuId());
            item.setSkuId(sku.getId());
            item.setName(sku.getName());
            item.setPrice(sku.getPrice());
            item.setNum(num);
            item.setMoney(num * item.getPrice());
            item.setImage(sku.getImage());
        }

        redis.opsForHash().put("cart_"+ username, skuId, item);
    }

    @Override
    public List<Object> get(String username) {
        return redis.opsForHash().values("cart_"+ username);
    }
}
