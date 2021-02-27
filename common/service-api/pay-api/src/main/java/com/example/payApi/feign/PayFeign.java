package com.example.payApi.feign;

import com.example.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author gong_da_kai
 * @version 1.0.0
 * @date 2021/2/26 16:18
 * @since 1.0.0
 */
@FeignClient("pay-server")
@RequestMapping("/pay")
public interface PayFeign {

    @GetMapping("/query/state")
    Result<Map<String, String>> queryState(@RequestParam("orderId") String orderId);
}
