package com.example.userApi.domain;

import java.io.Serializable;

public class Provinces implements Serializable {
    private String provinceid;

    private String province;

    public String getProvinceid() {
        return provinceid;
    }

    public void setProvinceid(String provinceid) {
        this.provinceid = provinceid;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Override
    public String toString() {
        return "Provinces{" +
                "provinceid='" + provinceid + '\'' +
                ", province='" + province + '\'' +
                '}';
    }
}