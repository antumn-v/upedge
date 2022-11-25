package com.upedge.oms.modules.order.request;

import lombok.Data;

import java.util.List;

@Data
public class OrderImportSaiheRequest {

    private Long orderId;

    private List<Long> orderIds;

}
