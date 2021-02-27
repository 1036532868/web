package com.example.orderApi.domain;

import java.io.Serializable;
import java.util.Date;

public class ReturnOrder implements Serializable {
    private Long id;

    private Long orderId;

    private Date applyTime;

    private Long userId;

    private String userAccount;

    private String linkman;

    private String linkmanMobile;

    private String type;

    private Integer returnMoney;

    private String isReturnFreight;

    private String status;

    private Date disposeTime;

    private Integer returnCause;

    private String evidence;

    private String description;

    private String remark;

    private Integer adminId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getLinkmanMobile() {
        return linkmanMobile;
    }

    public void setLinkmanMobile(String linkmanMobile) {
        this.linkmanMobile = linkmanMobile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getReturnMoney() {
        return returnMoney;
    }

    public void setReturnMoney(Integer returnMoney) {
        this.returnMoney = returnMoney;
    }

    public String getIsReturnFreight() {
        return isReturnFreight;
    }

    public void setIsReturnFreight(String isReturnFreight) {
        this.isReturnFreight = isReturnFreight;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDisposeTime() {
        return disposeTime;
    }

    public void setDisposeTime(Date disposeTime) {
        this.disposeTime = disposeTime;
    }

    public Integer getReturnCause() {
        return returnCause;
    }

    public void setReturnCause(Integer returnCause) {
        this.returnCause = returnCause;
    }

    public String getEvidence() {
        return evidence;
    }

    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    @Override
    public String toString() {
        return "ReturnOrder{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", applyTime=" + applyTime +
                ", userId=" + userId +
                ", userAccount='" + userAccount + '\'' +
                ", linkman='" + linkman + '\'' +
                ", linkmanMobile='" + linkmanMobile + '\'' +
                ", type='" + type + '\'' +
                ", returnMoney=" + returnMoney +
                ", isReturnFreight='" + isReturnFreight + '\'' +
                ", status='" + status + '\'' +
                ", disposeTime=" + disposeTime +
                ", returnCause=" + returnCause +
                ", evidence='" + evidence + '\'' +
                ", description='" + description + '\'' +
                ", remark='" + remark + '\'' +
                ", adminId=" + adminId +
                '}';
    }
}