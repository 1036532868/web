package com.example.order.mq.listener;

import com.example.exception.CRUDException;
import com.example.goodsApi.feign.SkuFeign;
import com.example.order.mapper.OrderItemMapper;
import com.example.order.mapper.OrderMapper;
import com.example.orderApi.domain.Order;
import com.example.orderApi.domain.OrderItem;
import com.example.payApi.feign.PayFeign;
import com.example.util.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gong_da_kai
 * @version 1.0.0
 * @date 2021/2/26 13:10
 * @since 1.0.0
 */
@Component
public class OrderListener {

    @Autowired
    private RedisTemplate<Object, Object> redis;
    @Autowired
    private ObjectMapper om;

    @Autowired
    private SkuFeign skuFeign;
    @Autowired
    private PayFeign payFeign;

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;

    /**
     * 监听订单超时队列
     * @param msg
     */
    @RabbitListener(queues = "q.order.timeout.handler")
    public void orderTimeoutHandler(String msg) throws CRUDException, ParseException {
        System.err.println("订单超时检查 -- " + new Date().toLocaleString());
        Order order = (Order) redis.opsForHash().get("order", msg);
        if (order == null) return;

        // 未支付时, 查询支付状态
        if ("0".equals(order.getPayStatus())) {
            Result<Map<String, String>> map = payFeign.queryState(msg);
            checkStatus(map.getData(), order);
        }

        // 清除缓存
        redis.opsForHash().delete("order", msg);
    }


    /**
     * 监听支付结果通知队列
     * @param msg
     * @throws JsonProcessingException
     * @throws CRUDException
     * @throws ParseException
     */
    @RabbitListener(queues = "q.order.pay")
    public void orderPay(String msg) throws JsonProcessingException, CRUDException, ParseException {
        System.err.println("支付结果通知 -- " + new Date().toLocaleString());

        System.err.println(msg);
        Map<String, String> map = om.readValue(msg, HashMap.class);
        System.err.println(map.get("out_trade_no"));
        Order order = (Order) redis.opsForHash().get("order", map.get("out_trade_no"));

        System.err.println(order);
        checkStatus(map, order);
    }

    /**
     * 检查支付结果, 根据支付结果作不同的应对
     * @param map
     */
    private void checkStatus(Map<String, String> map, Order order) throws CRUDException, ParseException {
        if ("FAIL".equals(map.get("return_code"))) throw new CRUDException("订单监听支付队列/延迟队列 + " + map.get("return_msg"));

        Date now = new Date();
        if ("FAIL".equals(map.get("result_code"))) {
            System.err.println("支付失败 == " + now.toLocaleString());

            // 超时未支付, 回滚库存, 关闭订单
            List<OrderItem> items = orderItemMapper.selectByOrderId(order.getId());

            for (OrderItem item : items) {
                Long skuId = item.getSkuId();
                Integer num = item.getNum();

                num = Integer.valueOf("-" + num.toString());
                skuFeign.sale(skuId, num);
            }

            order.setUpdateTime(now);
            order.setCloseTime(now);
            order.setPayStatus("2");
            orderMapper.updateByPrimaryKeySelective(order);

        } else {
            System.err.println("支付成功 == " + now.toLocaleString());

            String totalFee = map.get("total_fee");
            String transactionId = map.get("transaction_id");
            String timeEnd = map.get("time_end");

            //if (order.getTotalMoney() != Integer.valueOf(totalFee)) throw new CRUDException("虚假数据");
            if (1 != Integer.valueOf(totalFee)) throw new CRUDException("虚假数据");

            order.setUpdateTime(now);
            order.setPayTime(new SimpleDateFormat("yyyyMMddHHmmss").parse(timeEnd));
            order.setTransactionId(transactionId);
            order.setPayStatus("1");

            if (orderMapper.updateByPrimaryKeySelective(order) != 1) System.err.println("订单服务 -> orderMqListener -> 更改订单状态失败");

            // 更新 redis 中此订单的信息, 直到订单超时(订单创建30分钟后), 删除
            redis.opsForHash().put("order", order.getId(), order);
        }

    }

}
