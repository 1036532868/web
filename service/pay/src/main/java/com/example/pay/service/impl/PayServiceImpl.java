package com.example.pay.service.impl;

import com.example.exception.CRUDException;
import com.example.orderApi.domain.Order;
import com.example.pay.service.PayService;
import com.example.util.HttpClient;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gong_da_kai
 * @version 1.0.0
 * @date 2021/2/26 15:20
 * @since 1.0.0
 */
@Service
public class PayServiceImpl implements PayService {
    @Autowired
    private RedisTemplate<Object, Object> redis;
    @Autowired
    private AmqpTemplate amqp;

    @Value("${weixin.appid}")
    private String appId;
    @Value("${weixin.partner}")
    private String partner;
    @Value("${weixin.partnerkey}")
    private String partnerKey;
    @Value("${weixin.notifyurl}")
    private String url;

    @Override
    public String qrious(String orderId) throws Exception {

        Order order = (Order) redis.opsForHash().get("order", orderId);
        if (order == null) throw new CRUDException("没有找到此订单");

        /**
         * sign 在将 map 转换为 xml格式字符串时生成
         */
        Map<String, String> param = new HashMap<>();
        param.put("appid", appId);
        param.put("mch_id", partner);
        param.put("nonce_str", WXPayUtil.generateNonceStr());
        param.put("body", "web项目, 订单微信支付");
        param.put("attach", "附加数据");
        param.put("out_trade_no", order.getId());
        // 单位为分
        param.put("total_fee", "1");
        param.put("spbill_create_ip", "127.0.0.1");
        param.put("notify_url", url);
        param.put("trade_type", "NATIVE");
        param.put("product_id", "1148477873695236096");
        //httpParam.put("sign_type", "MD5");

        HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
        client.setXmlParam(WXPayUtil.generateSignedXml(param, partnerKey));
        client.setHttps(true);
        client.post();

        String res = client.getContent();
        Map<String, String> resMap = WXPayUtil.xmlToMap(res);
        if ("Fail".equals(resMap.get("return_code"))) throw new CRUDException(resMap.get("return_msg"));
        if ("Fail".equals(resMap.get("result_code"))) throw new CRUDException(resMap.get("err_code_des"));

        System.err.println("创建 "+ orderId +" 订单预支付");
        return resMap.get("code_url");
    }

    @Override
    public Map<String, String> queryState(String orderId) {

        Map<String, String> httpParam = new HashMap<>();

        /**
         * sign 在将 map 转换为 xml格式字符串时生成
         */
        httpParam.put("appid", appId);
        httpParam.put("mch_id", partner);
        httpParam.put("nonce_str", WXPayUtil.generateNonceStr());
        httpParam.put("out_trade_no", orderId);

        Map<String, String> result = null;
        try {
            String xml = WXPayUtil.generateSignedXml(httpParam, partnerKey);
            //System.err.println(xml);

            HttpClient http = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            http.setXmlParam(xml);
            http.setHttps(true);
            http.post();

            String resultXml = http.getContent();
            result = WXPayUtil.xmlToMap(resultXml);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

    }
}
