package com.upedge.oms.modules.wholesale.vo;

import lombok.Data;

@Data
public class WholesaleOrderExport {

    private String orderId;
    private String storeName;
    private String customerOrderNumber;
    private String shippingNumber;
    private String shippingMethod;
    private String payTime;
    private String shippingTime;

}
