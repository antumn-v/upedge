package com.upedge.ums.modules.account.entity.payoneer;

public class UserPayoneerAccount {
    private Long userId;

    private String accountId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    @Override
    public String toString() {
        return "UserPayoneerAccount{" +
                "userId=" + userId +
                ", accountId='" + accountId + '\'' +
                '}';
    }
}