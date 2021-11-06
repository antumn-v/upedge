package com.upedge.ums.modules.affiliate.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AdminCommissionRecordVo {

    /**
     *
     */
    private Long id;
    /**
     * 被推荐人ID
     */
    private Long refereeId;
    /**
     * 被推荐人姓名
     */
    private String refereeName;
    /**
     * 被推荐人邮箱
     */
    private String refereeEmail;
    /**
     *被推荐人账号
     */
    private String refereeLoginName;

    /**
     * 推荐人ID
     */
    private Long referrerId;

    /**
     * 推荐人姓名
     */
    private String referrerName;
    /**
     * 推荐人邮箱
     */
    private String referrerEmail;
    /**
     *推荐人账号
     */
    private String referrerLoginName;
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
    private BigDecimal commission;
    /**
     * 退款=0，支付=1 现在提现=2 余额提现=3 批发退款=4
     */
    private Integer state;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private Date updateTime;

}
