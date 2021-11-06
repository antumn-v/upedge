package com.upedge.ums.modules.account.entity.payoneer;

import lombok.Data;

/**
 * @author author
 */
@Data
public class PayoneerBalance {


    /**
     *
     */
    private String balanceId;
    /**
     *
     */
    private String accountId;
    /**
     *
     */
    private String statusName;
    /**
     *
     */
    private String currency;
    /**
     *
     */
    private String type;
    /**
     *
     */
    private String availableBalance;
    /**
     *
     */
    private String status;
    /**
     *
     */
    private String updateTime;

}
