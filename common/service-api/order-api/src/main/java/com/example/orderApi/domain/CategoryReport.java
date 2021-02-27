package com.example.orderApi.domain;

import java.io.Serializable;

public class CategoryReport extends CategoryReportKey implements Serializable {


    private Integer num;

    private Integer money;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }
    @Override
    public String toString() {
        return "CategoryReport{" +
                "num=" + num +
                ", money=" + money +
                '}';
    }
}