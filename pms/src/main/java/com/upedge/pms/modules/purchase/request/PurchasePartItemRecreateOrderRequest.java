package com.upedge.pms.modules.purchase.request;

import com.upedge.pms.modules.purchase.entity.PurchaseOrderItem;
import lombok.Data;

import java.util.List;

@Data
public class PurchasePartItemRecreateOrderRequest {

    private Long orderId;

    private List<Long> itemIds;
}
