package com.upedge.common.model.user.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class CommissionRecordVo {

    /**
     * 被推荐人ID
     */
    @NotNull
    private Long refereeId;

    @NotNull
    private Long referrerId;
    /**
     * 交易ID
     */
    @NotNull
    private Long orderId;
    /**
     * 0=普通订单，1=批发订单
     */
    private Integer orderType;
    /**
     * 每笔订单的佣金
     */
    @NotNull
    private BigDecimal commission;


    /**
     * 退款=0，支付=1 提现=2
     */
    @NotNull
    private Integer state;


}
