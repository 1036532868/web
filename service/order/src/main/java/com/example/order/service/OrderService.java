package com.example.order.service;

import com.example.exception.CRUDException;

/**
 * @author gong_da_kai
 * @version 1.0.0
 * @date 2021/2/26 10:43
 * @since 1.0.0
 */
public interface OrderService {
    String add(Long[] skuId, String username) throws CRUDException;

    String payStatus(String orderId) throws CRUDException;
}
