package com.example.order.mq.listener;

import com.example.goodsApi.feign.SkuFeign;
import com.example.order.mapper.OrderItemMapper;
import com.example.order.mapper.OrderMapper;
import com.example.orderApi.domain.Order;
import com.example.orderApi.domain.OrderItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;
import java.util.List;

/**
 * @author gong_da_kai
 * @version 1.0.0
 * @date 2021/2/26 13:10
 * @since 1.0.0
 */
public class OrderListener {

    @Autowired
    private RedisTemplate<Object, Object> redis;
    @Autowired
    private ObjectMapper om;

    @Autowired
    private SkuFeign skuFeign;

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;

    @RabbitListener(bindings =  @QueueBinding(value = @Queue("q.order.timeout.handler"),
            key = "order.timeout.handler",
            exchange = @Exchange(value = "ex.order.timeout.handler")))
    public void orderTimeoutHandler(String msg){
        Long orderId = om.convertValue(msg, Long.class);
        Order order = (Order) redis.opsForHash().get("order", orderId);

        if (order == null) return;
        Date closeTime = new Date();
        // 超时未支付, 回滚库存, 关闭订单
        if ("0".equals(order.getPayStatus())){
            List<OrderItem> items = orderItemMapper.selectByOrderId(order.getId());

            for (OrderItem item : items) {
                Long skuId = item.getSkuId();
                Integer num = item.getNum();

                num = Integer.valueOf("-" + num.toString());
                skuFeign.sale(skuId, num);
            }

            order.setUpdateTime(closeTime);
            order.setCloseTime(closeTime);
            orderMapper.updateByPrimaryKeySelective(order);
        }

        // 清除缓存
        redis.opsForHash().delete("order", orderId);
    }

}
