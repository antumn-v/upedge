package com.upedge.ums.modules.account.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class RechargeRecordVo {

    /**
     *
     */
    private Integer id;
    /**
     *
     */
    private Long rechargeId;
    /**
     *
     */
    private BigDecimal amount= BigDecimal.ZERO;
    /**
     *
     */
    private Long orderId;
    /**
     *
     */
    private Integer orderType;
    /**
     *
     */
    private Date createTime = new Date();

    private Integer seq;

    private Integer source;

    private RechargeLogVo rechargeLog;

}
