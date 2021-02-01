package com.example.goodsApi.domain;

import java.io.Serializable;
import java.util.Date;

public class StockBack extends StockBackKey implements Serializable {
    private Integer num;

    private String status;

    private Date createTime;

    private Date backTime;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getBackTime() {
        return backTime;
    }

    public void setBackTime(Date backTime) {
        this.backTime = backTime;
    }
}