package com.example.orderApi.domain;

import java.io.Serializable;

public class OrderConfig implements Serializable {
    private Integer id;

    private Integer orderTimeout;

    private Integer seckillTimeout;

    private Integer takeTimeout;

    private Integer serviceTimeout;

    private Integer commentTimeout;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderTimeout() {
        return orderTimeout;
    }

    public void setOrderTimeout(Integer orderTimeout) {
        this.orderTimeout = orderTimeout;
    }

    public Integer getSeckillTimeout() {
        return seckillTimeout;
    }

    public void setSeckillTimeout(Integer seckillTimeout) {
        this.seckillTimeout = seckillTimeout;
    }

    public Integer getTakeTimeout() {
        return takeTimeout;
    }

    public void setTakeTimeout(Integer takeTimeout) {
        this.takeTimeout = takeTimeout;
    }

    public Integer getServiceTimeout() {
        return serviceTimeout;
    }

    public void setServiceTimeout(Integer serviceTimeout) {
        this.serviceTimeout = serviceTimeout;
    }

    public Integer getCommentTimeout() {
        return commentTimeout;
    }

    public void setCommentTimeout(Integer commentTimeout) {
        this.commentTimeout = commentTimeout;
    }

    @Override
    public String toString() {
        return "OrderConfig{" +
                "id=" + id +
                ", orderTimeout=" + orderTimeout +
                ", seckillTimeout=" + seckillTimeout +
                ", takeTimeout=" + takeTimeout +
                ", serviceTimeout=" + serviceTimeout +
                ", commentTimeout=" + commentTimeout +
                '}';
    }
}