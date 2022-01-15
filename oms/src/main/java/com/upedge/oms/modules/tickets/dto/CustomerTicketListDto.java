package com.upedge.oms.modules.tickets.dto;

import lombok.Data;

@Data
public class CustomerTicketListDto {

    Long orderId;

    Long customerId;

    Integer state;

    String orderNumber;

    String storeName;

    Long managerCustomerId;
}
