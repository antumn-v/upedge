package com.upedge.oms.modules.order.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 客户管理 -- 个人客户  -- 用户信息 -- 潘达信息 返回vo
 */
@Data
public class PandaOrderListVo {

    /**
     * 订单id
     */
    private String id;

    /**
     * customer_id, -- 用户id  客户的用户id都以Customer表的id为准
     */
    private String customerId;

    /**
     * 交易号
     */
    private String paymentId;

    /**
     * 运输方式id
     */
    private String shipMethodId;

    /**
     * total_weight, -- 总重量
     */
    private BigDecimal totalWeight;

    /**
     * 目的地id
     */
    private String toAreaId;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * pay_state, -- 订单状态
     * 支付状态,待支付=0，已支付=1，取消订单=-1，支付中=2
     */
    private Integer payState;

    /**
     * ship_state, -- 物流状态
     * 0:未发货 1:已发货 2:已完成
     */
    private String shipState;

    /**
     * o.admin_user_id, -- 处理人id
     */
    private String adminUserId;

    /**
     * store_id, -- 店铺id
     */
    private String storeId;

    /**
     *ui.username,  -- 用户姓名
     */
    private String username;

    /**
     * ui.email, -- 用户邮箱
     */
    private String email;

    /**
     * u.login_name, -- 账号
     */
    private String loginName;

    /**
     * shippingMethodName, -- 运输方式
     */
    private String shippingMethodName;

    /**
     * toAreaName, -- 目的地
     */
    private String toAreaName;
    /**
     * managementName, -- 处理人
     */
    private String managementName;
    /**
     * store_name -- 店铺名称
     */
    private String storeName;
    /**
     * orderTrackingCount, -- 包裹数
     */
    private Long orderTrackingCount;

    /**
     * 订单收件人邮件
     */
    private String receiptEmail;

    /**
     * 收件人姓名
     */
    private String receiptName;

}
