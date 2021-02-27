package com.example.userApi.domain;

import java.io.Serializable;

public class Areas implements Serializable {
    private String areaid;

    private String area;

    private String cityid;

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    @Override
    public String toString() {
        return "Areas{" +
                "areaid='" + areaid + '\'' +
                ", area='" + area + '\'' +
                ", cityid='" + cityid + '\'' +
                '}';
    }
}