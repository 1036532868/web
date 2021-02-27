package com.example.pay.service;

import java.util.Map;

/**
 * @author gong_da_kai
 * @version 1.0.0
 * @date 2021/2/26 15:20
 * @since 1.0.0
 */
public interface PayService {
    String qrious(String orderId) throws Exception;

    Map<String, String> queryState(String orderId);
}
