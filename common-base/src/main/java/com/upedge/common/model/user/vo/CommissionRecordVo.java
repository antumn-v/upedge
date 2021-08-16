package com.upedge.common.model.user.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CommissionRecordVo {

    /**
     * 被推荐人ID
     */
    private Long refereeId;


    private Long referrerId;
    /**
     * 交易ID
     */
    private Long orderId;
    /**
     * 0=普通订单，1=批发订单
     */
    private Integer orderType;
    /**
     * 每笔订单的佣金
     */
    private BigDecimal orderAmount;


    /**
     * 退款=0，支付=1 提现=2
     */
    private Integer state;


}
