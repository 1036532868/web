package com.example.order.service;

import com.example.exception.CRUDException;

import java.util.List;

/**
 * @author gong_da_kai
 * @version 1.0.0
 * @date 2021/2/25 11:59
 * @since 1.0.0
 */
public interface CartService {
    void add(Long skuId, Integer num, String username) throws CRUDException;

    List<Object> get(String username);

    void delete(Long[] skuId, String username) throws CRUDException;

    void set(Long skuId, Integer num, String username);
}
