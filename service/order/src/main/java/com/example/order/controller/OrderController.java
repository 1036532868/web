package com.example.order.controller;

import com.example.exception.CRUDException;
import com.example.order.service.OrderService;
import com.example.userApi.domain.User;
import com.example.util.Result;
import com.example.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author gong_da_kai
 * @version 1.0.0
 * @date 2021/2/26 10:38
 * @since 1.0.0
 */
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    public Result<String> add(Long[] skuId, HttpSession session) throws CRUDException {
        String orderId = orderService.add(skuId, ((User)session.getAttribute("user")).getUsername());
        return new Result<>(true, StatusCode.OK, "", orderId);
    }

    /**
     * 根据 orderId 查询订单状态
     * @param orderId
     * @return
     */
    @GetMapping("/payStatus")
    public Result<String> payStatus(String orderId) throws CRUDException {
        String payStatus = orderService.payStatus(orderId);
        System.err.println("支付状态查询");
        switch (payStatus){
            case "0":
                payStatus = "未支付";
                break;
            case "1":
                payStatus = "已支付";
                break;
            case "2":
                payStatus = "支付失败";
                break;
        }
        return new Result<>(true, StatusCode.OK, "", payStatus);
    }
}
