package com.upedge.oms.modules.order.vo;

import lombok.Data;

import java.util.Date;

@Data
public class OrderStoreVo {

    /**
     *
     */
    private String platOrderId;
    /**
     *
     */
    private String platOrderName;
    /**
     *
     */
    private Long storeId;

    private String storeName;

    //0=已支付 1=部分退款 2=全部退款
    private Integer financialStatus;
    //0=未发货 1=部分发货 2=全部发货
    private Integer fulfillmentStatus;

    private Date createTime;

    private Date updateTime;

}
