package com.example.order.service.impl;

import com.example.exception.CRUDException;
import com.example.goodsApi.domain.Sku;
import com.example.goodsApi.feign.SkuFeign;
import com.example.order.mapper.OrderItemMapper;
import com.example.order.mapper.OrderMapper;
import com.example.order.service.OrderService;
import com.example.orderApi.domain.Order;
import com.example.orderApi.domain.OrderItem;
import com.example.util.IdWorker;
import com.example.util.Result;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author gong_da_kai
 * @version 1.0.0
 * @date 2021/2/26 10:43
 * @since 1.0.0
 */
@Service
@Transactional(rollbackFor = CRUDException.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private IdWorker idWorker;
    @Autowired
    private RedisTemplate<Object, Object> redis;
    @Autowired
    private AmqpTemplate amqp;

    @Autowired
    private SkuFeign skuFeign;

    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public String add(Long[] skuId, String username) throws CRUDException {
        String redisKey = "cart_"+ username;
        Long orderId = idWorker.nextId();
        Date date = new Date();
        Order order = new Order();
        order.setId(orderId.toString());
        // 写死了
        order.setPayType("1");
        order.setCreateTime(date);
        order.setUpdateTime(date);
        order.setUsername(username);
        order.setOrderStatus("0");
        order.setPayStatus("0");
        order.setConsignStatus("0");
        order.setIsDelete("0");

        // orderItem
        Integer totalNum = 0;
        Integer totalMoney = 0;
        for (Long id : skuId) {
            OrderItem item = (OrderItem) redis.opsForHash().get(redisKey, id);
            Result<Sku> res1 = skuFeign.selectById(id);
            Sku sku = res1.getData();
            if (sku == null) throw new CRUDException(res1.getMessage());

            item.setOrderId(orderId.toString());
            item.setPrice(sku.getPrice());
            item.setMoney(item.getPrice() * item.getNum());
            // 实付金额应减去优惠等
            item.setPayMoney(item.getMoney());

            totalNum += item.getNum();
            totalMoney += item.getMoney();

            skuFeign.sale(id, item.getNum());
            if (orderItemMapper.insertSelective(item) != 1) throw new CRUDException("生成订单失败");
        }

        order.setTotalNum(totalNum);
        order.setTotalMoney(totalMoney);

        if (orderMapper.insertSelective(order) != 1) throw new CRUDException("生成订单失败");

        amqp.convertAndSend("ex.order.timeout", "order.timeout", orderId);
        redis.opsForHash().put("order", orderId, order);
        // 清除购物车中已生成订单的商品
        redis.opsForHash().delete(redisKey, skuId);

        return orderId.toString();
    }
}
