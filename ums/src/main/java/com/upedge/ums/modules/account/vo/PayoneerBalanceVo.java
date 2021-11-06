package com.upedge.ums.modules.account.vo;

import lombok.Data;

@Data
public class PayoneerBalanceVo {

    Integer payoneerId;

    String payoneerAccountId;

    String balanceId;

    String usdBalance;
}
