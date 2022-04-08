package com.upedge.oms.modules.order.vo;

import lombok.Data;

@Data
public class ItemDischargeQuantityVo {

    Long variantId;

    String shippingWarehouse;

    Integer dischargeQuantity;
}
