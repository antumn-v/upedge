package com.upedge.oms.modules.tickets.dto;

import lombok.Data;

@Data
public class CustomerTicketListDto {

    Long customerId;

    Integer state;

    String orderNumber;

    String storeName;
}
