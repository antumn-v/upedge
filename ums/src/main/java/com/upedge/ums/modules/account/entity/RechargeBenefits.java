package com.upedge.ums.modules.account.entity;

import java.math.BigDecimal;
import java.util.Date;

public class RechargeBenefits {
    private Integer id;

    private Long applicationId;

    private BigDecimal amountStart;

    private BigDecimal amountEnd;

    private BigDecimal benefits;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public BigDecimal getAmountStart() {
        return amountStart;
    }

    public void setAmountStart(BigDecimal amountStart) {
        this.amountStart = amountStart;
    }

    public BigDecimal getAmountEnd() {
        return amountEnd;
    }

    public void setAmountEnd(BigDecimal amountEnd) {
        this.amountEnd = amountEnd;
    }

    public BigDecimal getBenefits() {
        return benefits;
    }

    public void setBenefits(BigDecimal benefits) {
        this.benefits = benefits;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}