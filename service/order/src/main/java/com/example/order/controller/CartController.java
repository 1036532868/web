package com.example.order.controller;

import com.example.exception.CRUDException;
import com.example.order.service.CartService;
import com.example.userApi.domain.User;
import com.example.util.Result;
import com.example.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author gong_da_kai
 * @version 1.0.0
 * @date 2021/2/25 11:53
 * @since 1.0.0
 */
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    /**
    * @description TODO 在当前用户的购物车中增加商品
    * @param skuId 商品id
    * @param num 数量
    * @param session HttpSession
    * @return com.example.util.Result
    * @author gong_da_kai
    * @date 2021/2/25 12:25
    * @since 1.0.0
    */
    @PostMapping("/add")
    public Result add(Long skuId, Integer num, HttpSession session) throws CRUDException {
        String username = ((User)session.getAttribute("user")).getUsername();

        cartService.add(skuId, num, username);
        System.err.println(1);
        return new Result();
    }

    /**
    * @description TODO 返回当前用户的购物车
    * @param session HttpSession
    * @return com.example.util.Result<java.util.List<java.lang.Object>>
    * @author gong_da_kai
    * @date 2021/2/25 12:25
    * @since 1.0.0
    */
    @GetMapping("/get")
    public Result<List<Object>> get(HttpSession session){
        String username = ((User)session.getAttribute("user")).getUsername();

        List<Object> l = cartService.get(username);
        System.err.println(2);
        return new Result<>(true, StatusCode.OK, "", l);
    }
}
