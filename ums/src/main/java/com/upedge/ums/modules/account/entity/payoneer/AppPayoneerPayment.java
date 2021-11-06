package com.upedge.ums.modules.account.entity.payoneer;

import com.upedge.common.model.account.payoneer.DebitResponse;

import java.util.Date;
import java.util.List;

public class AppPayoneerPayment {
    private Long id;

    private Long userId;

    private String accountId;

    private String paymentId;

    private String commitId;

    private Integer status;

    private String statusDescription;

    private Double fee;

    private Double chargedAmount;

    private Double targetAmount;

    private String createdAt;

    private String lastStatus;

    private String clientReferenceId;

    private Double requestAmount;

    private String requestCurrency;

    private String sourceCurrency;

    private String rate;

    private Date createTime;

    private Date updateTime;

    public AppPayoneerPayment() {
    }

    public AppPayoneerPayment(DebitResponse debit) {
        this.id = Long.parseLong(debit.getClient_reference_id());
        this.targetAmount = debit.getAmounts().getTarget().getAmount();
        this.chargedAmount = debit.getAmounts().getCharged().getAmount();
        this.requestAmount = debit.getAmounts().getTarget().getAmount();
        this.clientReferenceId = debit.getClient_reference_id();
        this.commitId = debit.getCommit_id();
        this.createdAt = debit.getCreated_at();
        this.fee = 0D;
        if(null != debit.getFees() && debit.getFees().size() > 0){
            List<DebitResponse.FeesBean> fees = debit.getFees();
            for (DebitResponse.FeesBean fee: fees) {
                this.fee += fee.getAmount();
            }
        }
        this.lastStatus = debit.getLast_status();
        this.paymentId = debit.getPayment_id();
        if(debit.getFx() != null){
            this.requestCurrency = debit.getFx().getTarget_currency();
            this.sourceCurrency = debit.getFx().getSource_currency();
            this.rate = debit.getFx().getRate();
        }else {
            this.requestCurrency = "USD";
            this.sourceCurrency = "USD";
            this.rate = "1";
        }
        this.status = debit.getStatus();
        this.statusDescription = debit.getStatus_description();
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId == null ? null : paymentId.trim();
    }

    public String getCommitId() {
        return commitId;
    }

    public void setCommitId(String commitId) {
        this.commitId = commitId == null ? null : commitId.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription == null ? null : statusDescription.trim();
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Double getChargedAmount() {
        return chargedAmount;
    }

    public void setChargedAmount(Double chargedAmount) {
        this.chargedAmount = chargedAmount;
    }

    public Double getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(Double targetAmount) {
        this.targetAmount = targetAmount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt == null ? null : createdAt.trim();
    }

    public String getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(String lastStatus) {
        this.lastStatus = lastStatus == null ? null : lastStatus.trim();
    }

    public String getClientReferenceId() {
        return clientReferenceId;
    }

    public void setClientReferenceId(String clientReferenceId) {
        this.clientReferenceId = clientReferenceId == null ? null : clientReferenceId.trim();
    }

    public Double getRequestAmount() {
        return requestAmount;
    }

    public void setRequestAmount(Double requestAmount) {
        this.requestAmount = requestAmount;
    }

    public String getRequestCurrency() {
        return requestCurrency;
    }

    public void setRequestCurrency(String requestCurrency) {
        this.requestCurrency = requestCurrency == null ? null : requestCurrency.trim();
    }

    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(String sourceCurrency) {
        this.sourceCurrency = sourceCurrency == null ? null : sourceCurrency.trim();
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate == null ? null : rate.trim();
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