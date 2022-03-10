package com.upedge.oms.modules.order.request;

import lombok.Data;

import java.util.List;

@Data
public class OrderItemQuoteRequest {

    List<Long> orderIds;


    Long itemId;

}
