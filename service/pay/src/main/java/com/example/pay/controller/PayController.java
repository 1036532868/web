package com.example.pay.controller;

import com.example.pay.service.PayService;
import com.example.util.Result;
import com.example.util.StatusCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author gong_da_kai
 * @version 1.0.0
 * @date 2021/2/26 15:07
 * @since 1.0.0
 */
@RestController
public class PayController {

    @Autowired
    private PayService payService;

    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private ObjectMapper om;

    /**
     * 发起预支付, 返回二维码链接
     * @param orderId
     * @return
     * @throws Exception
     */
    @GetMapping("/qrious")
    public Result<String> qrious(String orderId) throws Exception {
        String url = payService.qrious(orderId);

        return new Result<>(true, StatusCode.OK, "", url);
    }

    /**
     * 查询订单状态
     * @param orderId
     * @return
     */
    @GetMapping("/query/state")
    public Result<Map<String, String>> queryState(String orderId) {

        Map<String, String> result = payService.queryState(orderId);

        return new Result<>(true, StatusCode.OK, "查询订单支付状态成功", result);
    }

    /**
     * 微信支付结果通知 回调方法
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("/catch")
    public String catchResult(HttpServletRequest request) throws IOException {

        InputStream in = request.getInputStream();
        byte[] buffer = new byte[1024];

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int lenth = 0;
        while ((lenth = in.read(buffer)) != -1) {

            out.write(buffer, 0, lenth);

        }

        String result = out.toString("UTF-8");
        try {
            //System.err.println(result);
            Map<String, String> resultMap = WXPayUtil.xmlToMap(result);

            // 需要检查是否已经更改过状态, 防止重复修改数据, 可以在redis中存储当前订单的状态, 设置过期时间

            // 1.交换机名称 2.routing key 3.数据.toString()
            amqpTemplate.convertAndSend("ex.order.pay", "order.pay", om.writeValueAsString(resultMap));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.err.println("支付结果通知");

        return "<xml>\n" +
                "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "  <return_msg><![CDATA[OK]]></return_msg>\n" +
                "</xml>";
    }
}
