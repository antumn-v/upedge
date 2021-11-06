package com.upedge.ums.modules.account.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class AccountLogVo {


    /**
     *
     */
    private Integer id;
    /**
     *
     */
    private Long accountId;


    private Long customerId;

    private Integer transactionType;

    private Integer orderType;

    private Integer payMethod;
    /**
     *
     */
    private Long transactionId;
    /**
     *
     */
    private BigDecimal balance = BigDecimal.ZERO;
    /**
     *
     */
    private BigDecimal rebate = BigDecimal.ZERO;
    /**
     *
     */
    private BigDecimal credit = BigDecimal.ZERO;
    /**
     * 手续费
     */
    private BigDecimal fixFee;
    /**
     *
     */
    private Date createTime;

    private String transactionDesc;

    private List<RecordVo> recordList;

}
