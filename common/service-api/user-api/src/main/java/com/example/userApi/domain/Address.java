package com.example.userApi.domain;

import java.io.Serializable;

public class Address implements Serializable {
    private Integer id;

    private String username;

    private String provinceid;

    private String cityid;

    private String areaid;

    private String phone;

    private String address;

    private String contact;

    private String isDefault;

    private String alias;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProvinceid() {
        return provinceid;
    }

    public void setProvinceid(String provinceid) {
        this.provinceid = provinceid;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", provinceid='" + provinceid + '\'' +
                ", cityid='" + cityid + '\'' +
                ", areaid='" + areaid + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                ", isDefault='" + isDefault + '\'' +
                ", alias='" + alias + '\'' +
                '}';
    }
}