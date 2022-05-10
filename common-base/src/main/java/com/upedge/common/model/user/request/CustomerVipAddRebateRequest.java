package com.upedge.common.model.user.request;

import lombok.Data;

@Data
public class CustomerVipAddRebateRequest {

    Long customerId;

    Long orderId;
}
