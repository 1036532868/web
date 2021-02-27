package com.example.orderApi.domain;

import java.io.Serializable;

public class ReturnCause implements Serializable {
    private Integer id;

    private String cause;

    private Integer seq;

    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ReturnCause{" +
                "id=" + id +
                ", cause='" + cause + '\'' +
                ", seq=" + seq +
                ", status='" + status + '\'' +
                '}';
    }
}