package com.upedge.oms.modules.order.vo;

import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class AppStoreOrderVo {

    private Long orderId;

    private Long storeOrderId;

    private String platOrderName;

    private Integer financialStatus;

    private Integer fulfillmentStatus;

    private Date platOrderCreateTime;

    private String orderCustomerName;

    private Set<AppOrderItemVo> itemVos;

}
