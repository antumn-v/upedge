package com.upedge.common.model.pms.dto;

import lombok.Data;

import java.util.List;

@Data
public class CustomerStockPurchaseOrderRefundVo {

    private Long orderId;

    private String warehouseCode;

    private List<CustomerStockPurchaseOrderRefundItemVo> refundItemVos;

}
