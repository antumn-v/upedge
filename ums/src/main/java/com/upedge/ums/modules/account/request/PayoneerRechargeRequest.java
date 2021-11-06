package com.upedge.ums.modules.account.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 海桐
 */
@Data
public class PayoneerRechargeRequest extends ApplyRechargeRequest {

    @NotNull
    private String balanceId;

    @NotNull
    Integer payoneerId;
}
