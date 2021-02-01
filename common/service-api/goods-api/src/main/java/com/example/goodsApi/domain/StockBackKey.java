package com.example.goodsApi.domain;

import java.io.Serializable;

public class StockBackKey implements Serializable {
    private String orderId;

    private String skuId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }
}