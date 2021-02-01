package com.example.goodsApi.domain;

import java.io.Serializable;

public class CategoryBrandKey implements Serializable {
    private Integer categoryId;

    private Integer brandId;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }
}