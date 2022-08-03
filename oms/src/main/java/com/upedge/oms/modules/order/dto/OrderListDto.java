package com.upedge.oms.modules.order.dto;

import lombok.Data;


/**
 *  客户管理 -- 个人客户  -- 用户信息 -- 潘达信息 query 条件
 */
@Data
public class OrderListDto   {

    /**
     * customer_id 客户id
     */
    private String customerId;

    /**
     * 交易号
     */
    private String paymentId;

    /**
     * 订单号
     */
    private String id;

    /**
     *   pay_state   订单状态
     */
    private Integer payState;

    /**
     * ship_state 物流状态
     */
    private Integer shipState;

    /**
     * store_id 店铺id
     */
    private String storeId;

}
