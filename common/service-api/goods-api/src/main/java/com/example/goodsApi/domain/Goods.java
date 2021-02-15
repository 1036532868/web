package com.example.goodsApi.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @version 1.0.0
 * @Author gong_da_kai
 * @Date 2021/2/4 20:58
 * @since 1.0.0
 */
public class Goods implements Serializable {
    private Spu spu;
    private List<Sku> skuList;

    public Spu getSpu() {
        return spu;
    }

    public void setSpu(Spu spu) {
        this.spu = spu;
    }

    public List<Sku> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<Sku> skuList) {
        this.skuList = skuList;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "spu=" + spu +
                ", skuList=" + skuList +
                '}';
    }
}
