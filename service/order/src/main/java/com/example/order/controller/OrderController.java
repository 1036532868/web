package com.example.order.controller;

import com.example.exception.CRUDException;
import com.example.order.service.OrderService;
import com.example.userApi.domain.User;
import com.example.util.Result;
import com.example.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
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
}
